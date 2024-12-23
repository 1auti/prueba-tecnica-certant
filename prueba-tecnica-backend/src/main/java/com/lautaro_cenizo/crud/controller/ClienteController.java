package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.crud.dto.ClienteDTO;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(clienteService.guardarCliente(clienteDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        clienteService.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        return clienteService.obtenerClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.actualizar(id, clienteDTO));
    }

    @PutMapping("/{id}/incrementar-servicios")
    public ResponseEntity<Void> incrementarContadorServicios(@PathVariable Integer id) {
        clienteService.incrementarContadorServicios(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Cliente> buscarPorDNI(@RequestParam String dni) {
        return ResponseEntity.ok(clienteService.buscarPorDNI(dni));
    }

    @GetMapping("/premium")
    public ResponseEntity<List<Cliente>> obtenerClientesPremium() {
        return ResponseEntity.ok(clienteService.obtenerClientesPremium());
    }
}

