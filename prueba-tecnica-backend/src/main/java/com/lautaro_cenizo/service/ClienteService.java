package com.lautaro_cenizo.service;

import com.lautaro_cenizo.crud.dto.ClienteDTO;
import com.lautaro_cenizo.entity.cliente.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente guardarCliente(ClienteDTO clienteDTO);
    void eliminarCliente(Integer id);
    Optional<Cliente> obtenerClientePorId(Integer id);
    List<Cliente> listarClientes();
    Cliente actualizar(Integer id,ClienteDTO clienteDTO);
    void incrementarContadorServicios(Integer clienteId);
    Cliente buscarPorDNI(String dni);
    List<Cliente> obtenerClientesPremium();
}
