package com.lautaro_cenizo.crud.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Data
public class JornadaLaboralDTO {
    private LocalTime horaInicio;
    private LocalTime horaFinal;
}
