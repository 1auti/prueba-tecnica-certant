package com.lautaro_cenizo.entity.cita;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.entity.servicio.Servicio;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;
import com.lautaro_cenizo.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Citas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private LocalDateTime fecha;
    private BigDecimal precioFinal;
    @Enumerated(EnumType.STRING)
    private ESTADO proceso;
    private String notas;
    private Boolean esServicioGratis=false;


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    @JsonIgnore
    private Vehiculo vehiculo;

    @ManyToMany
    @JoinTable(
            name = "cita_servicios",
            joinColumns = @JoinColumn(name = "cita_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<Servicio> servicios = new ArrayList<>();

    @OneToOne
    @JsonIgnore
    private HistorialServicio historialServicio;

    //Metodos

    @PrePersist
    public void prePersist() {
        if (this.proceso == null) {
            this.proceso = ESTADO.ESPERA;
        }
    }

    public void calcularPrecioFinal() {
        if (esServicioGratis) {
            this.precioFinal = BigDecimal.ZERO;
            return;
        }

        BigDecimal total = servicios.stream()
                .map(servicio -> servicio.getTipoServicio().getPrecioBase())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (cliente.getEsPremuim()) {
            total = total.multiply(new BigDecimal("0.85"));
        }

        this.precioFinal = total;
    }

    public void aplicarServicioGratis() {
        if (!cliente.puedeUsarServicioGratis()) {
            throw new IllegalStateException("Cliente no elegible para servicio gratis");
        }
        this.esServicioGratis = true;
        cliente.usarServicioGratis();
        calcularPrecioFinal();
    }

    public boolean puedeSerCancelada() {
        return this.proceso == ESTADO.ESPERA &&
                LocalDateTime.now().isBefore(this.fecha.minusHours(24));
    }

    public void agregarServicio(Servicio servicio) {
        if (!servicio.isEstaActivo()) {
            throw new BusinessException("No se puede agregar un servicio inactivo");
        }
        servicios.add(servicio);
        servicio.getCitas().add(this);
        calcularPrecioFinal();
    }

    public void eliminarServicio(Servicio servicio) {
        if (!servicios.contains(servicio)) {
            throw new BusinessException("El servicio no existe en la cita");
        }
        servicios.remove(servicio);
        servicio.getCitas().remove(this);
        calcularPrecioFinal();
    }

}
