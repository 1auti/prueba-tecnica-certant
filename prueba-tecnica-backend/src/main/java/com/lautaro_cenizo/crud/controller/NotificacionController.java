package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.notificacion.Notificacion;
import com.lautaro_cenizo.entity.seguimiento.SeguimientoServicio;
import com.lautaro_cenizo.service.NotificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificacionController {

    @Autowired
    private NotificacionServicio notificacionServicio;

    @PostMapping("/recordatorio")
    public ResponseEntity<Void> crearNotificacionRecordatorio(@RequestBody Cita cita) {
        notificacionServicio.crearNotificacionRecordatorio(cita);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/email")
    public ResponseEntity<Void> enviarNotificacionEmail(@RequestBody Notificacion notificacion) {
        notificacionServicio.enviarNotificacionEmail(notificacion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/enviar-recordatorios")
    public ResponseEntity<Void> enviarRecordatoriosCitas() {
        notificacionServicio.enviarRecordatoriosCitas();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cambio-estado")
    public ResponseEntity<Void> notificarCambioEstado(@RequestBody SeguimientoServicio seguimiento) {
        notificacionServicio.notificarCambioEstado(seguimiento);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

