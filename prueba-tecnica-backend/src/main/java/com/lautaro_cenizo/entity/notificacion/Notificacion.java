package com.lautaro_cenizo.entity.notificacion;


import com.lautaro_cenizo.entity.cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String mensaje;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
    private boolean enviada;

    private String vehiculoMarca;
    private String vehiculoModelo;
    private String vehiculoPatente;

    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.enviada = false;
    }
}