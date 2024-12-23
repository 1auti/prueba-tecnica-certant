package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.email.EmailQueueService;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.notificacion.Notificacion;
import com.lautaro_cenizo.entity.notificacion.TipoNotificacion;
import com.lautaro_cenizo.entity.seguimiento.CambioEstado;
import com.lautaro_cenizo.entity.seguimiento.SeguimientoServicio;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;
import com.lautaro_cenizo.entity.cita.CitaRepository;
import com.lautaro_cenizo.entity.notificacion.NotificacionRepository;
import com.lautaro_cenizo.service.NotificacionServicio;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@Transactional
public class NotificacionServiceImpl implements NotificacionServicio {

    private final NotificacionRepository notificacionRepository;
    private final JavaMailSender emailSender;
    private final CitaRepository citaRepository;
    private final EmailQueueService emailQueueService;
    private final SpringTemplateEngine templateEngine;

    @Value("${MAIL_FROM}")
    private String emailFrom;

    public NotificacionServiceImpl(
            NotificacionRepository notificacionRepository,
            JavaMailSender emailSender,
            CitaRepository citaRepository,
            EmailQueueService emailQueueService,
            SpringTemplateEngine templateEngine) {
        this.notificacionRepository = notificacionRepository;
        this.emailSender = emailSender;
        this.citaRepository = citaRepository;
        this.emailQueueService = emailQueueService;
        this.templateEngine = templateEngine;
    }


    @Override
    public void crearNotificacionRecordatorio(Cita cita) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(TipoNotificacion.RECORDATORIO_CITA);
        notificacion.setCliente(cita.getCliente());
        notificacion.setMensaje("Recordatorio: Tiene una cita programada para mañana a las " +
                cita.getFecha().format(DateTimeFormatter.ofPattern("HH:mm")));

        notificacionRepository.save(notificacion);
    }


    @Override
    @Retryable(
            value = {MessagingException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void enviarNotificacionEmail(Notificacion notificacion) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(emailFrom);
            helper.setTo(notificacion.getCliente().getEmail());
            helper.setSubject("Notificación Boutique Automotor");

            Context context = new Context();
            context.setVariable("clienteNombre", notificacion.getCliente().getNombre());
            context.setVariable("estadoServicio", notificacion.getTipo().toString());
            context.setVariable("descripcionEstado", notificacion.getMensaje());

            // Ahora usamos los datos del vehículo que guardamos en la notificación
            if (notificacion.getTipo() == TipoNotificacion.CONTROL_CALIDAD) {
                context.setVariable("vehiculoMarca", notificacion.getVehiculoMarca());
                context.setVariable("vehiculoModelo", notificacion.getVehiculoModelo());
                context.setVariable("vehiculoPatente", notificacion.getVehiculoPatente());
            }

            String htmlContent = templateEngine.process("servicio-update", context);
            helper.setText(htmlContent, true);

            emailSender.send(message);

            notificacion.setEnviada(true);
            notificacion.setFechaEnvio(LocalDateTime.now());
            notificacionRepository.save(notificacion);

            log.info("Email enviado exitosamente a: {}", notificacion.getCliente().getEmail());

        } catch (MessagingException e) {
            log.error("Error al enviar email de notificación", e);
            throw new BusinessException("Error al enviar la notificación por email");
        }
    }

    @Override
    @Scheduled(cron = "0 0 20 * * *") // Se ejecuta todo los dias a las 20hs
    public void enviarRecordatoriosCitas() {
        LocalDateTime maniana = LocalDateTime.now().plusDays(1);
        List<Cita> citasManiana = citaRepository.findByFechaBetween(
                maniana.withHour(0).withMinute(0),
                maniana.withHour(23).withMinute(59)
        );

        for (Cita cita : citasManiana) {
            crearNotificacionRecordatorio(cita);
        }
    }

    @Override
    public void notificarCambioEstado(SeguimientoServicio seguimiento) {
        // Obtener el último cambio de estado
        CambioEstado ultimoCambio = seguimiento.getHistorialCambios()
                .get(seguimiento.getHistorialCambios().size() - 1);

        // Crear nueva notificación
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(TipoNotificacion.CONTROL_CALIDAD);
        notificacion.setCliente(seguimiento.getHistorialServicio().getCliente());

        // Generar mensaje personalizado usando el método existente
        String mensaje = generarMensajeCambioEstado(seguimiento, ultimoCambio);
        notificacion.setMensaje(mensaje);

        // Guardar los datos del vehículo en la notificación
        notificacion.setVehiculoMarca(seguimiento.getHistorialServicio().getCita().getVehiculo().getMarca());
        notificacion.setVehiculoModelo(seguimiento.getHistorialServicio().getCita().getVehiculo().getModelo());
        notificacion.setVehiculoPatente(seguimiento.getHistorialServicio().getCita().getVehiculo().getPatente());

        // Guardar la notificación
        notificacionRepository.save(notificacion);

        // Enviar el email
        enviarNotificacionEmail(notificacion);

        log.info("Notificación de cambio de estado enviada al cliente: {}",
                seguimiento.getHistorialServicio().getCliente().getEmail());
    }

    private String generarMensajeCambioEstado(SeguimientoServicio seguimiento, CambioEstado cambio) {
        Cliente cliente = seguimiento.getHistorialServicio().getCliente();
        Vehiculo vehiculo = seguimiento.getHistorialServicio().getCita().getVehiculo();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Estimado/a ").append(cliente.getNombre())
                .append(" ").append(cliente.getApellido())
                .append(",\n\n");

        mensaje.append("Le informamos que su vehículo ")
                .append(vehiculo.getMarca())
                .append(" ")
                .append(vehiculo.getModelo())
                .append(" (Patente: ")
                .append(vehiculo.getPatente())
                .append(") ");

        // Personalizar mensaje según el estado
        switch (cambio.getEstadoNuevo()) {
            case EN_DIAGNOSTICO:
                mensaje.append("ha ingresado a diagnóstico. Nuestros técnicos están evaluando el vehículo.");
                break;

            case ESPERANDO_REPUESTOS:
                mensaje.append("está en espera de repuestos necesarios para completar el servicio. " +
                        "Le informaremos cuando estén disponibles.");
                break;

            case EN_REPARACION:
                mensaje.append("ha iniciado el proceso de reparación/servicio.");
                break;

            case EN_CONTROL_CALIDAD:
                mensaje.append("está en la etapa final de control de calidad.");
                break;

            case LISTO_PARA_ENTREGAR:
                mensaje.append("está listo para ser retirado. " +
                        "Puede pasar por nuestro taller en el horario de 8:00 a 18:00 hs.");
                break;

            case ENTREGADO:
                mensaje.append("ha sido entregado. Esperamos que esté satisfecho con nuestro servicio.");
                break;

            default:
                mensaje.append("ha actualizado su estado a: ").append(cambio.getEstadoNuevo());
        }

        mensaje.append("\n\n");

        // Agregar comentarios si existen
        if (cambio.getMotivo() != null && !cambio.getMotivo().isEmpty()) {
            mensaje.append("Comentarios adicionales: ").append(cambio.getMotivo()).append("\n\n");
        }

        // Agregar información de contacto
        mensaje.append("Si tiene alguna consulta, no dude en contactarnos:\n");
        mensaje.append("- Teléfono: (XXX) XXX-XXXX\n");
        mensaje.append("- Email: contacto@boutiqueautomotor.com\n\n");
        mensaje.append("Saludos cordiales,\n");
        mensaje.append("Boutique Automotor");

        return mensaje.toString();
    }

//    private void enviarNotificacionEmail(Notificacion notificacion) {
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//        try {
//            helper.setFrom(emailFrom);
//            helper.setTo(notificacion.getCliente().getEmail());
//            helper.setSubject("Actualización de Estado - Boutique Automotor");
//            helper.setText(notificacion.getMensaje());
//
//            emailSender.send(message);
//
//            notificacion.setEnviada(true);
//            notificacion.setFechaEnvio(LocalDateTime.now());
//            notificacionRepository.save(notificacion);
//
//        } catch (MessagingException e) {
//            log.error("Error al enviar email de notificación", e);
//            throw new BusinessException("Error al enviar la notificación por email");
//        }
//    }




}

