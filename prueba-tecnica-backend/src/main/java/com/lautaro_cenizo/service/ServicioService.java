package com.lautaro_cenizo.service;

import com.lautaro_cenizo.crud.dto.ServicioDTO;
import com.lautaro_cenizo.entity.servicio.Servicio;
import com.lautaro_cenizo.entity.servicio.TipoServicio;

import java.util.List;

public interface ServicioService {
    Servicio crear(ServicioDTO servicioDTO);
    Servicio actualizar(Integer id, ServicioDTO servicioDTO);
    Servicio obtenerPorId(Integer id);
    List<Servicio> listarTodos();
    List<Servicio> listarActivos();
    List<Servicio> listarPorTipo(TipoServicio tipo);
    void eliminar(Integer id);
    void activar(Integer id);
    void desactivar(Integer id);
}
