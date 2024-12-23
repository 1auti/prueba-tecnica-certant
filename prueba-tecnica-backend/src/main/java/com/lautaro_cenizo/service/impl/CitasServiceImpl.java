package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.crud.dto.CitaDTO;
import com.lautaro_cenizo.entity.notificacion.Notificacion;
import com.lautaro_cenizo.entity.notificacion.TipoNotificacion;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.exception.ResourceNotFoundException;
import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.servicio.Servicio;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;
import com.lautaro_cenizo.entity.cita.CitaRepository;
import com.lautaro_cenizo.entity.cliente.ClienteRepository;
import com.lautaro_cenizo.entity.servicio.ServicioRepository;
import com.lautaro_cenizo.entity.vehiculo.VehiculoRepository;
import com.lautaro_cenizo.service.CitasService;
import com.lautaro_cenizo.service.NotificacionServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CitasServiceImpl implements CitasService {

    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ServicioRepository servicioRepository;
    private final NotificacionServicio notificacionServicio;

    public CitasServiceImpl(CitaRepository citaRepository,
                            ClienteRepository clienteRepository,
                            VehiculoRepository vehiculoRepository,
                            ServicioRepository servicioRepository,
                            NotificacionServicio notificacionServicio) {
        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.servicioRepository = servicioRepository;
        this.notificacionServicio = notificacionServicio;
    }

    @Override
    public Cita crearCita(CitaDTO citaDTO) {
        log.info("Creando nueva cita para cliente ID: {}", citaDTO.getClienteId());

        Cliente cliente = clienteRepository.findById(citaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(citaDTO.getVehiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        List<Servicio> servicios = servicioRepository.findAllById(citaDTO.getServiciosIds());

        if (servicios.size() != citaDTO.getServiciosIds().size()) {
            throw new ResourceNotFoundException("Uno o más servicios no encontrados");
        }

        if (!verificarDisponibilidad(citaDTO.getFecha())) {
            throw new BusinessException("No hay disponibilidad para la fecha seleccionada");
        }

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        servicios.forEach(cita::agregarServicio);
        cita.setFecha(citaDTO.getFecha());
        cita.setNotas(citaDTO.getNotas());
        cita.calcularPrecioFinal();

        cliente.agregarCita(cita);
        Cita citaGuardada = citaRepository.save(cita);

        // Crear notificación de confirmación
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(TipoNotificacion.CONFIRMACION_CITA);
        notificacion.setCliente(cliente);
        notificacion.setMensaje(generarMensajeConfirmacion(citaGuardada));
        notificacionServicio.enviarNotificacionEmail(notificacion);


        return citaGuardada;
    }

    private String generarMensajeConfirmacion(Cita cita) {
        return String.format(
                "Su cita ha sido confirmada para el %s a las %s.\n" +
                        "Vehículo: %s %s (Patente: %s)\n" +
                        "Servicios programados:\n%s",
                cita.getFecha().toLocalDate(),
                cita.getFecha().format(DateTimeFormatter.ofPattern("HH:mm")),
                cita.getVehiculo().getMarca(),
                cita.getVehiculo().getModelo(),
                cita.getVehiculo().getPatente(),
                cita.getServicios().stream()
                        .map(servicio -> String.format("- %s: %s",
                                servicio.getTipoServicio().getDescripcion(),
                                servicio.getTipoServicio().getPrecioBase()))
                        .collect(Collectors.joining("\n"))
        );
    }

    @Override
    public void cancelarCita(Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        if (!cita.puedeSerCancelada()) {
            throw new BusinessException("La cita no puede ser cancelada");
        }

        citaRepository.delete(cita);
        log.info("Cita ID: {} cancelada exitosamente", id);
    }


    @Override
    public List<Cita> obtenerCitasPorCliente(Integer clienteId) {
        return citaRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Cita> obtenerCitasFuturas() {
        return citaRepository.findCitasFuturas(LocalDateTime.now());
    }

    @Override
    public boolean verificarDisponibilidad(LocalDateTime fecha) {
        return  citaRepository.countCitasByFecha(fecha) < 3; //Serian maximo 3 citas por horario
    }
}
