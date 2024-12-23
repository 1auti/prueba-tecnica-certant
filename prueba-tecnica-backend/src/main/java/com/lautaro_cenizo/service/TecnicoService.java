package com.lautaro_cenizo.service;

import com.lautaro_cenizo.crud.dto.JornadaLaboralDTO;
import com.lautaro_cenizo.crud.dto.TecnicoDTO;
import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.entity.tecnico.DiasSemana;
import com.lautaro_cenizo.entity.tecnico.Especialidad;
import com.lautaro_cenizo.entity.tecnico.Tecnico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TecnicoService {
    Tecnico crear(TecnicoDTO tecnicoDTO);
    Tecnico actualizar(Integer id, TecnicoDTO tecnicoDTO);
    Tecnico obtenerPorId(Integer id);
    Tecnico obtenerPorCardId(Long cardId);
    List<Tecnico> listarTodos();
    List<Tecnico> listarActivos();
    List<Tecnico> listarPorEspecialidad(Especialidad especialidad);
    void eliminar(Integer id);
    void registrarServicio(Integer tecnicoId, HistorialServicio servicio);
    boolean verificarDisponibilidad(Integer tecnicoId, LocalDateTime fecha);
    void actualizarJornadaLaboral(Integer tecnicoId, Map<DiasSemana, JornadaLaboralDTO> jornadaLaboral);
}
