package com.lautaro_cenizo.controller;

import com.lautaro_cenizo.crud.dto.CitaDTO;
import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.service.CitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:4200")
public class CitasController {

    @Autowired
    private CitasService citasService;

    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody CitaDTO citaDTO) {
        return new ResponseEntity<>(citasService.crearCita(citaDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Integer id) {
        citasService.cancelarCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Cita>> obtenerCitasPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(citasService.obtenerCitasPorCliente(clienteId));
    }

    @GetMapping("/futuras")
    public ResponseEntity<List<Cita>> obtenerCitasFuturas() {
        return ResponseEntity.ok(citasService.obtenerCitasFuturas());
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        return ResponseEntity.ok(citasService.verificarDisponibilidad(fecha));
    }
}

