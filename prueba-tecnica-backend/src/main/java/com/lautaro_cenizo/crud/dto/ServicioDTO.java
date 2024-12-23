package com.lautaro_cenizo.crud.dto;

import com.lautaro_cenizo.entity.servicio.TipoServicio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {
    @NotBlank(message = "La descripción es requerida")
    private String descripcion;
    @NotNull(message = "El tipo de servicio es requerido")
    private TipoServicio tipoServicio;
    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;

}
