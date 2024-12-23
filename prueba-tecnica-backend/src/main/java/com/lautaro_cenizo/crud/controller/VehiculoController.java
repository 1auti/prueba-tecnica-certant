package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.crud.dto.VehiculoDTO;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;
import com.lautaro_cenizo.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody VehiculoDTO vehiculoDTO) {
        return new ResponseEntity<>(vehiculoService.crear(vehiculoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Integer id, @RequestBody VehiculoDTO vehiculoDTO) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, vehiculoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(vehiculoService.obtenerPorId(id));
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> obtenerPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(vehiculoService.obtenerPorPatente(patente));
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarTodos() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Vehiculo>> listarPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(vehiculoService.listarPorCliente(clienteId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        vehiculoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{vehiculoId}/cambiar-cliente/{nuevoClienteId}")
    public ResponseEntity<Void> cambiarCliente(@PathVariable Integer vehiculoId, @PathVariable Integer nuevoClienteId) {
        vehiculoService.cambiarCliente(vehiculoId, nuevoClienteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

