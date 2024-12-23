package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.crud.dto.ServicioDTO;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.exception.ResourceNotFoundException;
import com.lautaro_cenizo.entity.servicio.Servicio;
import com.lautaro_cenizo.entity.servicio.TipoServicio;
import com.lautaro_cenizo.entity.servicio.ServicioRepository;
import com.lautaro_cenizo.service.ServicioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public Servicio crear(ServicioDTO servicioDTO) {
        log.info("Creando nuevo servicio de tipo: {}", servicioDTO.getTipoServicio());

        Servicio servicio = new Servicio();
        BeanUtils.copyProperties(servicioDTO, servicio, "id");
        servicio.setEstaActivo(true);

        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio actualizar(Integer id, ServicioDTO servicioDTO) {
        log.info("Actualizando servicio con ID: {}", id);

        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        BeanUtils.copyProperties(servicioDTO, servicio, "id");
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio obtenerPorId(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    @Override
    public List<Servicio> listarActivos() {
        return servicioRepository.findByEstaActivo(true);
    }

    @Override
    public List<Servicio> listarPorTipo(TipoServicio tipo) {
        return servicioRepository.findByTipoServicio(tipo);
    }

    @Override
    public void eliminar(Integer id) {
        log.info("Eliminando servicio con ID: {}", id);

        if (!servicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Servicio no encontrado");
        }
        servicioRepository.deleteById(id);
    }

    @Override
    public void activar(Integer id) {
        log.info("Activando servicio con ID: {}", id);

        Servicio servicio = obtenerPorId(id);
        if (servicio.isEstaActivo()) {
            throw new BusinessException("El servicio ya está activo");
        }

        servicio.setEstaActivo(true);
        servicioRepository.save(servicio);
    }

    @Override
    public void desactivar(Integer id) {
        log.info("Desactivando servicio con ID: {}", id);

        Servicio servicio = obtenerPorId(id);
        if (!servicio.isEstaActivo()) {
            throw new BusinessException("El servicio ya está desactivado");
        }

        servicio.setEstaActivo(false);
        servicioRepository.save(servicio);
    }
}
