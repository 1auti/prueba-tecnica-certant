package com.lautaro_cenizo.entity.seguimiento;

import com.lautaro_cenizo.entity.historial.HistorialServicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SeguimientoServicio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeguimientoServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private HistorialServicio historialServicio;

    @Enumerated(EnumType.STRING)
    private EstadoDetallado estado;

    private LocalDateTime fechaActualizacion;
    private String comentarios;

    @OneToMany(mappedBy = "seguimiento", cascade = CascadeType.ALL)
    private List<CambioEstado> historialCambios = new ArrayList<>();
}
