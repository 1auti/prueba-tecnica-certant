package com.lautaro_cenizo.service;

import com.lautaro_cenizo.crud.dto.VehiculoDTO;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;

import java.util.List;

public interface VehiculoService {
    Vehiculo crear(VehiculoDTO vehiculoDTO);
    Vehiculo actualizar(Integer id, VehiculoDTO vehiculoDTO);
    Vehiculo obtenerPorId(Integer id);
    Vehiculo obtenerPorPatente(String patente);
    List<Vehiculo> listarTodos();
    List<Vehiculo> listarPorCliente(Integer clienteId);
    void eliminar(Integer id);
    void cambiarCliente(Integer vehiculoId, Integer nuevoClienteId);
}
