package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.entity.seguimiento.EstadoDetallado;
import com.lautaro_cenizo.entity.tecnico.Tecnico;
import com.lautaro_cenizo.service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seguimientos")
@CrossOrigin(origins = "http://localhost:4200")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @PutMapping("/{seguimientoId}/actualizar-estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Integer seguimientoId,
            @RequestParam EstadoDetallado nuevoEstado,
            @RequestParam String motivo,
            @RequestBody Tecnico tecnico) {
        seguimientoService.actualizarEstado(seguimientoId, nuevoEstado, motivo, tecnico);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

