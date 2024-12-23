package com.lautaro_cenizo.crud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialServicioDTO {
    private Integer id;
    private Integer citaId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completadoALas;
    @NotBlank(message = "Las observaciones son requeridas")
    private String observaciones;
    @NotBlank(message = "Los detalles del trabajo son requeridos")
    private String detallesTrabajo;
    private Boolean controlCalidadChequiado;
    @NotNull(message = "El técnico es requerido")
    private Integer tecnicoId;
    @NotNull(message = "El cliente es requerido")
    private Integer clienteId;
    @Min(0) @Max(10)
    private Integer calificacionServicio;
}
