package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.crud.dto.VehiculoDTO;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.exception.ResourceNotFoundException;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;
import com.lautaro_cenizo.entity.cliente.ClienteRepository;
import com.lautaro_cenizo.entity.vehiculo.VehiculoRepository;
import com.lautaro_cenizo.service.VehiculoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, ClienteRepository clienteRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Vehiculo crear(VehiculoDTO vehiculoDTO) {
        log.info("Creando nuevo vehículo con patente: {}", vehiculoDTO.getPatente());

        if (vehiculoRepository.existsByPatente(vehiculoDTO.getPatente())) {
            throw new BusinessException("Ya existe un vehículo con esa patente");
        }

        Cliente cliente = clienteRepository.findById(vehiculoDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Vehiculo vehiculo = new Vehiculo();
        BeanUtils.copyProperties(vehiculoDTO, vehiculo, "id");
        vehiculo.setCliente(cliente);

        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo actualizar(Integer id, VehiculoDTO vehiculoDTO) {
        log.info("Actualizando vehículo con ID: {}", id);

        // Buscar el vehículo existente
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        // Verificar si la nueva patente ya existe en otro vehículo
        if (!vehiculo.getPatente().equals(vehiculoDTO.getPatente()) &&
                vehiculoRepository.existsByPatente(vehiculoDTO.getPatente())) {
            throw new BusinessException("Ya existe un vehículo con esa patente");
        }

        // Si se está cambiando el cliente, verificar que exista
        if (!vehiculo.getCliente().getId().equals(vehiculoDTO.getClienteId())) {
            Cliente nuevoCliente = clienteRepository.findById(vehiculoDTO.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
            vehiculo.setCliente(nuevoCliente);
        }

        // Actualizar los campos del vehículo
        vehiculo.setPatente(vehiculoDTO.getPatente());
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setAnio(vehiculoDTO.getAnio());
        vehiculo.setColor(vehiculoDTO.getColor());
        vehiculo.setTiposVehiculo(vehiculoDTO.getTiposVehiculo());


        // Guardar y retornar el vehículo actualizado
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo obtenerPorId(Integer id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Override
    public Vehiculo obtenerPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente).orElse(null);
}

    @Override
    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public List<Vehiculo> listarPorCliente(Integer clienteId) {
        return vehiculoRepository.findByClienteId(clienteId);
    }

    @Override
    public void eliminar(Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehiculo con id " + id + " no encontrado");
        }
        vehiculoRepository.deleteById(id);
    }

    @Override
    public void cambiarCliente(Integer vehiculoId, Integer nuevoClienteId) {
        Vehiculo vehiculo = obtenerPorId(vehiculoId);
        Cliente nuevoCliente = clienteRepository.findById(nuevoClienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        vehiculo.setCliente(nuevoCliente);
        vehiculoRepository.save(vehiculo);
        log.info("Vehículo ID: {} asignado a nuevo cliente ID: {}", vehiculoId, nuevoClienteId);
    }
}
