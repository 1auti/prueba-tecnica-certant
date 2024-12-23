package com.lautaro_cenizo.entity.repuesto;

import com.lautaro_cenizo.entity.historial.HistorialServicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "RepuestosUsados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepuestoUsado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Repuesto repuesto;

    @ManyToOne
    private HistorialServicio historialServicio;

    private Integer cantidad;
    private BigDecimal precioUnitario;

    @PrePersist
    public void prePersist() {
        this.precioUnitario = repuesto.getPrecio();
    }
}

