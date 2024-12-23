package com.lautaro_cenizo.entity.tecnico;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * Clase que no tiene su propia tabla en la base de datos, sino que sus atributos se almacenan como parte de la tabla de otra entidad que la contiene.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JornadaLaboral {
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public boolean incluyeHorario(LocalTime horario) {
        return !horario.isBefore(horaInicio) && !horario.isAfter(horaFin);
    }
}
