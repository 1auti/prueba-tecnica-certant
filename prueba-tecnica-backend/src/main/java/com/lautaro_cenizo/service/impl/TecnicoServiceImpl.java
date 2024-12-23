package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.crud.dto.JornadaLaboralDTO;
import com.lautaro_cenizo.crud.dto.TecnicoDTO;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.exception.ResourceNotFoundException;
import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.entity.tecnico.DiasSemana;
import com.lautaro_cenizo.entity.tecnico.Especialidad;
import com.lautaro_cenizo.entity.tecnico.JornadaLaboral;
import com.lautaro_cenizo.entity.tecnico.Tecnico;
import com.lautaro_cenizo.entity.tecnico.TecnicoRepository;
import com.lautaro_cenizo.service.TecnicoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoServiceImpl(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public Tecnico crear(TecnicoDTO tecnicoDTO) {
        log.info("Creando nuevo técnico con DNI: {}", tecnicoDTO.getDni());

        if (tecnicoRepository.findByCardId(tecnicoDTO.getCardId()).isPresent()) {
            throw new BusinessException("Ya existe un técnico con ese Card ID");
        }

        Tecnico tecnico = new Tecnico();
        BeanUtils.copyProperties(tecnicoDTO, tecnico, "id");
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public Tecnico actualizar(Integer id, TecnicoDTO tecnicoDTO) {
        log.info("Actualizando técnico con ID: {}", id);

        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado"));

        // Verificar si el nuevo cardId no está en uso por otro técnico
        if (!tecnico.getCardId().equals(tecnicoDTO.getCardId()) &&
                tecnicoRepository.findByCardId(tecnicoDTO.getCardId()).isPresent()) {
            throw new BusinessException("Ya existe un técnico con ese Card ID");
        }

        BeanUtils.copyProperties(tecnicoDTO, tecnico, "id");
        return tecnicoRepository.save(tecnico);
    }

    @Override
    public Tecnico obtenerPorId(Integer id) {
        return tecnicoRepository.findById(id).orElse(null);
    }

    @Override
    public Tecnico obtenerPorCardId(Long cardId) {
        return tecnicoRepository.findByCardId(cardId).orElse(null);
    }

    @Override
    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll();
    }

    @Override
    public List<Tecnico> listarActivos() {
        return tecnicoRepository.findByEstaActivo(true);
    }

    @Override
    public List<Tecnico> listarPorEspecialidad(Especialidad especialidad) {
        return tecnicoRepository.findTecnicosDisponiblesPorEspecialidad(especialidad);
    }

    @Override
    public void eliminar(Integer id) {
        log.info("Eliminando técnico con ID: {}", id);

        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico no encontrado"));

        tecnicoRepository.deleteById(id);
    }

    @Override
    public void registrarServicio(Integer tecnicoId, HistorialServicio servicio) {
        log.info("Registrando servicio para técnico con ID: {}", tecnicoId);

        Tecnico tecnico = obtenerPorId(tecnicoId);
        tecnico.agregarServicioCompletado(servicio);
        tecnicoRepository.save(tecnico);
    }

    @Override
    public boolean verificarDisponibilidad(Integer tecnicoId, LocalDateTime fecha) {
        Tecnico tecnico = obtenerPorId(tecnicoId);
        return tecnico.tieneDisponibilidad(fecha);
    }

    @Override
    public void actualizarJornadaLaboral(Integer tecnicoId, Map<DiasSemana, JornadaLaboralDTO> jornadaLaboralDTOMap) {
        log.info("Actualizando jornada laboral para técnico con ID: {}", tecnicoId);

        Tecnico tecnico = obtenerPorId(tecnicoId);

        // Convertir DTO a entidad y actualizar
        jornadaLaboralDTOMap.forEach((dia, dto) -> {
            JornadaLaboral nuevaJornada = new JornadaLaboral(dto.getHoraInicio(), dto.getHoraFinal());
            tecnico.getJornadaLaboral().put(dia, nuevaJornada);
        });

        tecnicoRepository.save(tecnico);
        log.info("Jornada laboral actualizada para técnico con ID: {}", tecnicoId);
    }
}
