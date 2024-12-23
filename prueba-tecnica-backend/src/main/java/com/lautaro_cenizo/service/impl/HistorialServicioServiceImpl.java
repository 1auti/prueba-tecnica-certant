package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.crud.dto.HistorialServicioDTO;
import com.lautaro_cenizo.entity.notificacion.Notificacion;
import com.lautaro_cenizo.entity.notificacion.TipoNotificacion;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.exception.ResourceNotFoundException;
import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.entity.tecnico.Tecnico;
import com.lautaro_cenizo.entity.cita.CitaRepository;
import com.lautaro_cenizo.entity.cliente.ClienteRepository;
import com.lautaro_cenizo.entity.historial.HistorialServicioRepository;
import com.lautaro_cenizo.entity.tecnico.TecnicoRepository;
import com.lautaro_cenizo.service.ClienteService;
import com.lautaro_cenizo.service.HistorialServicioService;
import com.lautaro_cenizo.service.NotificacionServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class HistorialServicioServiceImpl implements HistorialServicioService {

    private final HistorialServicioRepository historialServicioRepository;
    private final CitaRepository citaRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;
    private final NotificacionServicio notificacionServicio;

    public HistorialServicioServiceImpl(HistorialServicioRepository historialServicioRepository, CitaRepository citaRepository, TecnicoRepository tecnicoRepository, ClienteRepository clienteRepository, ClienteService clienteService, NotificacionServicio notificacionServicio) {
        this.historialServicioRepository = historialServicioRepository;
        this.citaRepository = citaRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
        this.notificacionServicio = notificacionServicio;
    }

    @Override
    public HistorialServicio crear(HistorialServicioDTO historialDTO) {
        Cita cita = citaRepository.findById(historialDTO.getCitaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        Tecnico tecnico = tecnicoRepository.findById(historialDTO.getTecnicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado"));

        Cliente cliente = clienteRepository.findById(historialDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        HistorialServicio historial = new HistorialServicio();
        BeanUtils.copyProperties(historialDTO, historial, "id");
        historial.setCita(cita);
        historial.setTecnico(tecnico);
        historial.setCliente(cliente);

        historial.completarServicio();
        historialServicioRepository.save(historial);
        clienteService.incrementarContadorServicios(historialDTO.getClienteId());

        // Crear notificación de servicio completado
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(TipoNotificacion.SERVICIO_COMPLETADO);
        notificacion.setCliente(cliente);
        notificacion.setMensaje(generarMensajeServicioCompletado(historial));
        notificacionServicio.enviarNotificacionEmail(notificacion);

        return historial;
    }

    private String generarMensajeServicioCompletado(HistorialServicio historial) {
        return String.format(
                "Servicio completado para su vehículo %s %s (Patente: %s)\n" +
                        "Servicios realizados:\n%s\n" +
                        "Técnico: %s\n" +
                        "Observaciones: %s",
                historial.getCita().getVehiculo().getMarca(),
                historial.getCita().getVehiculo().getModelo(),
                historial.getCita().getVehiculo().getPatente(),
                historial.getCita().getServicios().stream()
                        .map(servicio -> String.format("- %s",
                                servicio.getTipoServicio().getDescripcion()))
                        .collect(Collectors.joining("\n")),
                historial.getTecnico().getNombre(),
                historial.getObservaciones()
        );
    }


    @Override
    public HistorialServicio actualizar(Integer id, HistorialServicioDTO historialDTO) {
        return null;
    }

    @Override
    public Optional<HistorialServicio> obtenerPorId(Integer id) {
        return historialServicioRepository.findById(id);
    }

    @Override
    public List<HistorialServicio> listarTodos() {
        return historialServicioRepository.findAll();
    }

    @Override
    public List<HistorialServicio> listarPorCliente(Integer clienteId) {
        return historialServicioRepository.findByClienteId(clienteId);
    }

    @Override
    public List<HistorialServicio> listarPorTecnico(Integer tecnicoId) {
        return historialServicioRepository.findByTecnicoId(tecnicoId);
    }

    @Override
    public void eliminar(Integer id) {
        if (!historialServicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente con id " + id + " no encontrado");
        }
        historialServicioRepository.deleteById(id);
    }


    @Override
    public void calificarServicio(Integer id, int calificacion) {
        if (calificacion < 0 || calificacion > 10) {
            throw new BusinessException("La calificación debe estar entre 0 y 10");
        }

        HistorialServicio historial = obtenerPorId(id).orElseThrow(null);
        historial.calificarServicio(calificacion);
        historialServicioRepository.save(historial);
    }

    @Override
    public void marcarControlCalidad(Integer id, boolean aprobado) {
        HistorialServicio historial = obtenerPorId(id).orElseThrow(null);
        historial.setControlCalidadChequiado(aprobado);
        historialServicioRepository.save(historial);
    }
}
