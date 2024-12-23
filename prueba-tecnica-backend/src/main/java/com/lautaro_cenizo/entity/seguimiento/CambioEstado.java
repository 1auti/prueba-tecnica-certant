package com.lautaro_cenizo.entity.seguimiento;

import com.lautaro_cenizo.entity.tecnico.Tecnico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "CambioEstado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambioEstado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SeguimientoServicio seguimiento;

    @Enumerated(EnumType.STRING)
    private EstadoDetallado estadoAnterior;

    @Enumerated(EnumType.STRING)
    private EstadoDetallado estadoNuevo;

    private LocalDateTime fechaCambio;
    private String motivo;

    @ManyToOne
    private Tecnico realizadoPor;
}

