package com.lautaro_cenizo.entity.vehiculo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lautaro_cenizo.common.BaseEntity;
import com.lautaro_cenizo.entity.cliente.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Vehiculo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vehiculo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = "^(([A-Z]{3}\\d{3})|([A-Z]{2}\\d{3}[A-Z]{2}))$",
            message = "La patente debe tener formato AAA111 o AA111AA")
    private String patente;
    private Integer anio;
    private String modelo;
    private String marca;
    private String color;
    @Enumerated(EnumType.STRING)
    private TiposVehiculo tiposVehiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @PrePersist
    @PreUpdate
    private void validarPatente() {
        if (!patente.matches("^(([A-Z]{3}\\d{3})|([A-Z]{2}\\d{3}[A-Z]{2}))$")) {
            throw new IllegalArgumentException("Formato de patente inválido");
        }
    }

}
