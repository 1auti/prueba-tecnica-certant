package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.crud.dto.HistorialServicioDTO;
import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.service.HistorialServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-servicios")
@CrossOrigin(origins = "http://localhost:4200")
public class HistorialServicioController {

    @Autowired
    private HistorialServicioService historialServicioService;

    @PostMapping
    public ResponseEntity<HistorialServicio> crear(@RequestBody HistorialServicioDTO historialDTO) {
        return new ResponseEntity<>(historialServicioService.crear(historialDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialServicio> actualizar(@PathVariable Integer id, @RequestBody HistorialServicioDTO historialDTO) {
        return ResponseEntity.ok(historialServicioService.actualizar(id, historialDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialServicio> obtenerPorId(@PathVariable Integer id) {
        return historialServicioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<HistorialServicio>> listarTodos() {
        return ResponseEntity.ok(historialServicioService.listarTodos());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<HistorialServicio>> listarPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(historialServicioService.listarPorCliente(clienteId));
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<HistorialServicio>> listarPorTecnico(@PathVariable Integer tecnicoId) {
        return ResponseEntity.ok(historialServicioService.listarPorTecnico(tecnicoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        historialServicioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/calificar")
    public ResponseEntity<Void> calificarServicio(@PathVariable Integer id, @RequestParam int calificacion) {
        historialServicioService.calificarServicio(id, calificacion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/control-calidad")
    public ResponseEntity<Void> marcarControlCalidad(@PathVariable Integer id, @RequestParam boolean aprobado) {
        historialServicioService.marcarControlCalidad(id, aprobado);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

