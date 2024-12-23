package com.lautaro_cenizo.entity.cliente;

import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.historial.HistorialServicio;
import com.lautaro_cenizo.entity.persona.Persona;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {


    private Boolean esPremuim = false;
    private int contadorServicios = 0;
    private Boolean servicioGratisDisponible=false;
    private LocalDateTime fechasUltimosServiciosGratis;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Cita> citas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialServicio> historialServicios = new ArrayList<>();


    //Metodos utiles
    public void incrementarContadorServicios() {
        this.contadorServicios++;
        if (this.contadorServicios >= 5 && !this.esPremuim) {
            this.esPremuim = true;
            this.servicioGratisDisponible = true;
        }
    }
    public boolean puedeUsarServicioGratis() {
        return this.esPremuim && this.servicioGratisDisponible;
    }

    public void usarServicioGratis() {
        if (!puedeUsarServicioGratis()) {
            throw new IllegalStateException("No hay servicio gratis disponible");
        }
        this.servicioGratisDisponible = false;
        this.fechasUltimosServiciosGratis = LocalDateTime.now();
    }

    private void verificarEstadoPremium() {
        this.esPremuim = this.contadorServicios > 5;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        vehiculo.setCliente(this);
    }

    public void agregarCita(Cita cita) {
        citas.add(cita);
        cita.setCliente(this);
    }
}
