package com.lautaro_cenizo.crud.dto;

import com.lautaro_cenizo.entity.vehiculo.TiposVehiculo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private Integer id;
    @Pattern(regexp = "^(([A-Z]{3}\\d{3})|([A-Z]{2}\\d{3}[A-Z]{2}))$",
            message = "Formato de patente inválido")
    private String patente;
    @Min(value = 1900, message = "Año inválido")
    private Integer anio;
    @NotBlank(message = "El modelo es requerido")
    private String modelo;
    @NotBlank(message = "La marca es requerida")
    private String marca;
    @NotNull(message = "El cliente es requerido")
    private Integer clienteId;
    @NotNull(message = "El color es requerido")
    private String color;
    @NotNull(message = "El Tipo vehiculo es requerido")
    private TiposVehiculo tiposVehiculo;

}
