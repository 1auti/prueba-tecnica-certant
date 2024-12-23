package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.crud.dto.ServicioDTO;
import com.lautaro_cenizo.entity.servicio.Servicio;
import com.lautaro_cenizo.entity.servicio.TipoServicio;
import com.lautaro_cenizo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:4200")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // Crear un nuevo servicio
    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody ServicioDTO servicioDTO) {
        Servicio servicio = servicioService.crear(servicioDTO);
        return new ResponseEntity<>(servicio, HttpStatus.CREATED);
    }

    // Actualizar un servicio existente
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Integer id, @RequestBody ServicioDTO servicioDTO) {
        Servicio servicioActualizado = servicioService.actualizar(id, servicioDTO);
        return ResponseEntity.ok(servicioActualizado);
    }

    // Obtener un servicio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable Integer id) {
        Servicio servicio = servicioService.obtenerPorId(id);
        if (servicio != null) {
            return ResponseEntity.ok(servicio);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Listar todos los servicios
    @GetMapping
    public ResponseEntity<List<Servicio>> listarTodos() {
        List<Servicio> servicios = servicioService.listarTodos();
        return ResponseEntity.ok(servicios);
    }

    // Listar los servicios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Servicio>> listarActivos() {
        List<Servicio> serviciosActivos = servicioService.listarActivos();
        return ResponseEntity.ok(serviciosActivos);
    }

    // Listar los servicios por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Servicio>> listarPorTipo(@PathVariable TipoServicio tipo) {
        List<Servicio> serviciosPorTipo = servicioService.listarPorTipo(tipo);
        return ResponseEntity.ok(serviciosPorTipo);
    }

    // Eliminar un servicio por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Activar un servicio
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Integer id) {
        servicioService.activar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Desactivar un servicio
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Integer id) {
        servicioService.desactivar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
