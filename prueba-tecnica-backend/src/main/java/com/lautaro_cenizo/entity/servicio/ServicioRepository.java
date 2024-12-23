package com.lautaro_cenizo.entity.servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    List<Servicio> findByTipoServicio(TipoServicio tipoServicio);
    List<Servicio> findByEstaActivo(Boolean estaActivo);
    List<Servicio> findByTipoServicioAndEstaActivo(TipoServicio tipoServicio, Boolean estaActivo);
}