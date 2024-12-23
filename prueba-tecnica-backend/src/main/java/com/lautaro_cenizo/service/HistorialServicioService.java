package com.lautaro_cenizo.service;

import com.lautaro_cenizo.crud.dto.HistorialServicioDTO;
import com.lautaro_cenizo.entity.historial.HistorialServicio;

import java.util.List;
import java.util.Optional;

public interface HistorialServicioService {
    HistorialServicio crear(HistorialServicioDTO historialDTO);
    HistorialServicio actualizar(Integer id, HistorialServicioDTO historialDTO);
    Optional<HistorialServicio> obtenerPorId(Integer id);
    List<HistorialServicio> listarTodos();
    List<HistorialServicio> listarPorCliente(Integer clienteId);
    List<HistorialServicio> listarPorTecnico(Integer tecnicoId);
    void eliminar(Integer id);
    void calificarServicio(Integer id, int calificacion);
    void marcarControlCalidad(Integer id, boolean aprobado);
}