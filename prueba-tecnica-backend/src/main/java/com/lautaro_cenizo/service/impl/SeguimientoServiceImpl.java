package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.entity.notificacion.Notificacion;
import com.lautaro_cenizo.entity.notificacion.TipoNotificacion;
import com.lautaro_cenizo.entity.seguimiento.CambioEstado;
import com.lautaro_cenizo.entity.seguimiento.EstadoDetallado;
import com.lautaro_cenizo.entity.seguimiento.SeguimientoRepository;
import com.lautaro_cenizo.entity.seguimiento.SeguimientoServicio;
import com.lautaro_cenizo.entity.tecnico.Tecnico;
import com.lautaro_cenizo.exception.ResourceNotFoundException;
import com.lautaro_cenizo.service.NotificacionServicio;
import com.lautaro_cenizo.service.SeguimientoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.lautaro_cenizo.entity.seguimiento.EstadoDetallado.*;

@Service
@Transactional
@Slf4j
public class SeguimientoServiceImpl implements SeguimientoService {
    private final SeguimientoRepository seguimientoRepository;
    private final NotificacionServicio notificacionServicio;

    public SeguimientoServiceImpl(SeguimientoRepository seguimientoRepository, NotificacionServicio notificacionServicio) {
        this.seguimientoRepository = seguimientoRepository;
        this.notificacionServicio = notificacionServicio;
    }

    @Override
    public void actualizarEstado(Integer seguimientoId, EstadoDetallado nuevoEstado, String motivo, Tecnico tecnico) {
        SeguimientoServicio seguimiento = seguimientoRepository
                .findById(seguimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Seguimiento no encontrado"));

        EstadoDetallado estadoAnterior = seguimiento.getEstado();

        CambioEstado cambio = new CambioEstado();
        cambio.setEstadoAnterior(estadoAnterior);
        cambio.setEstadoNuevo(nuevoEstado);
        cambio.setFechaCambio(LocalDateTime.now());
        cambio.setMotivo(motivo);
        cambio.setRealizadoPor(tecnico);

        seguimiento.getHistorialCambios().add(cambio);
        seguimiento.setEstado(nuevoEstado);
        seguimiento.setFechaActualizacion(LocalDateTime.now());

        seguimientoRepository.save(seguimiento);

        // Notificaciones especiales según el estado
        switch (nuevoEstado) {
            case EN_DIAGNOSTICO:
                notificarInicioDiagnostico(seguimiento, cambio);
                break;
            case ESPERANDO_REPUESTOS:
                notificarEsperaRepuestos(seguimiento, cambio);
                break;
            case EN_REPARACION:
                notificarInicioReparacion(seguimiento, cambio);
                break;
            case EN_CONTROL_CALIDAD:
                notificarControlCalidad(seguimiento, cambio);
                break;
            case LISTO_PARA_ENTREGAR:
                notificarListoParaEntregar(seguimiento, cambio);
                break;
            default:
                notificacionServicio.notificarCambioEstado(seguimiento);
        }
    }

    private void notificarInicioDiagnostico(SeguimientoServicio seguimiento, CambioEstado cambio) {
        Notificacion notificacion = crearNotificacionBase(seguimiento, TipoNotificacion.INICIO_DIAGNOSTICO);
        notificacion.setMensaje("Su vehículo ha ingresado a diagnóstico con el técnico " +
                cambio.getRealizadoPor().getNombre());
        notificacionServicio.enviarNotificacionEmail(notificacion);
    }

    private void notificarEsperaRepuestos(SeguimientoServicio seguimiento, CambioEstado cambio) {
        Notificacion notificacion = crearNotificacionBase(seguimiento, TipoNotificacion.ESPERA_REPUESTOS);
        notificacion.setMensaje("Su vehículo está en espera de repuestos. " +
                "Le notificaremos cuando estén disponibles. " +
                "Motivo: " + cambio.getMotivo());
        notificacionServicio.enviarNotificacionEmail(notificacion);
    }

    private void notificarInicioReparacion(SeguimientoServicio seguimiento, CambioEstado cambio) {
        Notificacion notificacion = crearNotificacionBase(seguimiento, TipoNotificacion.INICIO_REPARACION);
        notificacion.setMensaje("Su vehículo ha iniciado el proceso de reparación con el técnico " +
                cambio.getRealizadoPor().getNombre());
        notificacionServicio.enviarNotificacionEmail(notificacion);
    }

    private void notificarControlCalidad(SeguimientoServicio seguimiento, CambioEstado cambio) {
        Notificacion notificacion = crearNotificacionBase(seguimiento, TipoNotificacion.CONTROL_CALIDAD);
        notificacion.setMensaje("Su vehículo está en la etapa final de control de calidad");
        notificacionServicio.enviarNotificacionEmail(notificacion);
    }

    private void notificarListoParaEntregar(SeguimientoServicio seguimiento, CambioEstado cambio) {
        Notificacion notificacion = crearNotificacionBase(seguimiento, TipoNotificacion.LISTO_ENTREGA);
        notificacion.setMensaje("Su vehículo está listo para ser retirado. Horario de atención: 8:00 a 18:00 hs");
        notificacionServicio.enviarNotificacionEmail(notificacion);
    }

    private Notificacion crearNotificacionBase(SeguimientoServicio seguimiento, TipoNotificacion tipo) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(tipo);
        notificacion.setCliente(seguimiento.getHistorialServicio().getCliente());
        notificacion.setVehiculoMarca(seguimiento.getHistorialServicio().getCita().getVehiculo().getMarca());
        notificacion.setVehiculoModelo(seguimiento.getHistorialServicio().getCita().getVehiculo().getModelo());
        notificacion.setVehiculoPatente(seguimiento.getHistorialServicio().getCita().getVehiculo().getPatente());
        return notificacion;
    }
}