package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.crud.dto.JornadaLaboralDTO;
import com.lautaro_cenizo.crud.dto.TecnicoDTO;
import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.entity.tecnico.DiasSemana;
import com.lautaro_cenizo.entity.tecnico.Especialidad;
import com.lautaro_cenizo.entity.tecnico.Tecnico;
import com.lautaro_cenizo.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tecnicos")
@CrossOrigin(origins = "http://localhost:4200")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @PostMapping
    public ResponseEntity<Tecnico> crear(@RequestBody TecnicoDTO tecnicoDTO) {
        return new ResponseEntity<>(tecnicoService.crear(tecnicoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> actualizar(@PathVariable Integer id, @RequestBody TecnicoDTO tecnicoDTO) {
        return ResponseEntity.ok(tecnicoService.actualizar(id, tecnicoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tecnicoService.obtenerPorId(id));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<Tecnico> obtenerPorCardId(@PathVariable Long cardId) {
        return ResponseEntity.ok(tecnicoService.obtenerPorCardId(cardId));
    }

    @GetMapping
    public ResponseEntity<List<Tecnico>> listarTodos() {
        return ResponseEntity.ok(tecnicoService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Tecnico>> listarActivos() {
        return ResponseEntity.ok(tecnicoService.listarActivos());
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<Tecnico>> listarPorEspecialidad(@PathVariable Especialidad especialidad) {
        return ResponseEntity.ok(tecnicoService.listarPorEspecialidad(especialidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tecnicoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{tecnicoId}/registrar-servicio")
    public ResponseEntity<Void> registrarServicio(@PathVariable Integer tecnicoId, @RequestBody HistorialServicio servicio) {
        tecnicoService.registrarServicio(tecnicoId, servicio);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{tecnicoId}/disponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @PathVariable Integer tecnicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {
        return ResponseEntity.ok(tecnicoService.verificarDisponibilidad(tecnicoId, fecha));
    }

    @PutMapping("/{tecnicoId}/jornada-laboral")
    public ResponseEntity<Void> actualizarJornadaLaboral(
            @PathVariable Integer tecnicoId,
            @RequestBody Map<DiasSemana, JornadaLaboralDTO> jornadaLaboral) {
        tecnicoService.actualizarJornadaLaboral(tecnicoId, jornadaLaboral);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

