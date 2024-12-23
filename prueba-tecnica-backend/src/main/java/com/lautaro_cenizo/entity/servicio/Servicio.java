package com.lautaro_cenizo.entity.servicio;

import com.lautaro_cenizo.entity.cita.Cita;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Servicio")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoServicio tipoServicio;

    @ManyToMany(mappedBy = "servicios")
    private List<Cita> citas = new ArrayList<>();

    private boolean estaActivo = false ;

}
