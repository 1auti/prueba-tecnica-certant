package com.lautaro_cenizo.crud.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CitaDTO {
    private Integer clienteId;
    private Integer vehiculoId;
    private List<Integer> serviciosIds;
    private LocalDateTime fecha;
    private String notas;
}
