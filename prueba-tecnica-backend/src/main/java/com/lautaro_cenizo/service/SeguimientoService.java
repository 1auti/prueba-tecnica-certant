package com.lautaro_cenizo.service;


import com.lautaro_cenizo.entity.seguimiento.EstadoDetallado;
import com.lautaro_cenizo.entity.tecnico.Tecnico;

public interface SeguimientoService {

    void actualizarEstado(Integer seguimientoId, EstadoDetallado nuevoEstado,
                          String motivo, Tecnico tecnico);

}
