package com.lautaro_cenizo.entity.tecnico;

import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.entity.persona.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "Tecnicos")
@AllArgsConstructor
@NoArgsConstructor
public class Tecnico extends Persona {

    @Column(name = "card_id", unique = true)
    private Long cardId;
    private Especialidad especialidad;
    private Boolean estaActivo = true;
    @ElementCollection
    @CollectionTable(
            name = "jornadas_laborales",
            joinColumns = @JoinColumn(name = "tecnico_id")
    )
    private Map<DiasSemana, JornadaLaboral> jornadaLaboral = new HashMap<>();
    private BigDecimal salario;

    @OneToMany(
            mappedBy = "tecnico",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<HistorialServicio> serviciosCompletados = new ArrayList<>();


    // Métodos útiles
    public void agregarServicioCompletado(HistorialServicio servicio) {
        serviciosCompletados.add(servicio);
        servicio.setTecnico(this);
    }

    public boolean tieneDisponibilidad(LocalDateTime fecha) {
        DiasSemana dia = DiasSemana.valueOf(fecha.getDayOfWeek().name());
        JornadaLaboral jornada = jornadaLaboral.get(dia);
        return jornada != null && jornada.incluyeHorario(fecha.toLocalTime());
    }

    public void actualizarJornadaLaboral(DiasSemana dia, JornadaLaboral nuevaJornada) {
        jornadaLaboral.put(dia, nuevaJornada);
    }


}
