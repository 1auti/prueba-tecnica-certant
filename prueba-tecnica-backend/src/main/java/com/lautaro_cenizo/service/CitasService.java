package com.lautaro_cenizo.service;

import com.lautaro_cenizo.crud.dto.CitaDTO;
import com.lautaro_cenizo.entity.cita.Cita;

import java.time.LocalDateTime;
import java.util.List;

public interface CitasService {
    Cita crearCita(CitaDTO citaDTO);
    void cancelarCita(Integer id);
    List<Cita> obtenerCitasPorCliente(Integer clienteId);
    List<Cita> obtenerCitasFuturas();
    boolean verificarDisponibilidad(LocalDateTime fecha);
}
