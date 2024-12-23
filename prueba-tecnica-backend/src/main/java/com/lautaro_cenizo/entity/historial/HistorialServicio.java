package com.lautaro_cenizo.entity.historial;

import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.repuesto.Repuesto;
import com.lautaro_cenizo.entity.repuesto.RepuestoUsado;
import com.lautaro_cenizo.entity.tecnico.Tecnico;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HistorialServicio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistorialServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @Column(name = "completadoALas")
    private LocalDateTime completadoALas;
    //Antes del trabajo
    private String observaciones;
    //Despues del trabajo
    private String detallesTrabajo;
    private Boolean controlCalidadChequiado;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private boolean asistio = true;

    @Min(0)
    @Max(10)
    private Integer calificacionServicio;

    @OneToMany(mappedBy = "historialServicio", cascade = CascadeType.ALL)
    private List<RepuestoUsado> repuestosUsados = new ArrayList<>();


    public void marcarAsistido() {
        this.completarServicio();
        setAsistio(true);
        // Lógica adicional para marcar el servicio como asistido
    }

    public void agregarRepuesto(Repuesto repuesto, Integer cantidad) {
        if (repuesto.getStock() < cantidad) {
            throw new BusinessException("Stock insuficiente");
        }

        RepuestoUsado repuestoUsado = new RepuestoUsado();
        repuestoUsado.setRepuesto(repuesto);
        repuestoUsado.setCantidad(cantidad);
        repuestoUsado.setHistorialServicio(this);

        repuestosUsados.add(repuestoUsado);
        repuesto.setStock(repuesto.getStock() - cantidad);
    }


    public void completarServicio() {
        this.completadoALas = LocalDateTime.now();
        this.controlCalidadChequiado = false;  // Requiere chequeo posterior
    }

    public void calificarServicio(int calificacion) {
        if (calificacion < 0 || calificacion > 10) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 10");
        }
        this.calificacionServicio = calificacion;
    }

}
