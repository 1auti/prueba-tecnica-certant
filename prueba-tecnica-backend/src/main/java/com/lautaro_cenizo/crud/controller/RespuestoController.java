package com.lautaro_cenizo.crud.controller;

import com.lautaro_cenizo.service.RespuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/repuestos")
@CrossOrigin(origins = "http://localhost:4200")
public class RespuestoController {

    @Autowired
    private RespuestoService respuestoService;

    @PostMapping("/alerta-stock-bajo")
    public ResponseEntity<Void> alertaStockBajo() {
        respuestoService.alertaStockBajo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

