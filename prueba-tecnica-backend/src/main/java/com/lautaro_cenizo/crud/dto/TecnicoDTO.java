package com.lautaro_cenizo.crud.dto;

import com.lautaro_cenizo.entity.tecnico.DiasSemana;
import com.lautaro_cenizo.entity.tecnico.Especialidad;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnicoDTO {
    private Integer id;
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "El apellido es requerido")
    private String apellido;
    @Email(message = "Email inválido")
    private String email;
    @NotBlank(message = "El DNI es requerido")
    private String dni;
    private String telefono;
    @NotNull(message = "El cardId es requerido")
    private Long cardId;
    @NotNull(message = "La especialidad es requerida")
    private Especialidad especialidad;
    private Boolean estaActivo;
    @NotNull(message = "El salario es requerido")
    @Positive(message = "El salario debe ser positivo")
    private BigDecimal salario;
    private Map<DiasSemana, JornadaLaboralDTO> jornadaLaboral;
}