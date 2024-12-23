package com.lautaro_cenizo.entity.historial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistorialServicioRepository extends JpaRepository<HistorialServicio, Integer> {
    List<HistorialServicio> findByClienteId(Integer clienteId);
    List<HistorialServicio> findByTecnicoId(Integer tecnicoId);
    List<HistorialServicio> findByControlCalidadChequiado(Boolean controlCalidadChequiado);

    @Query("SELECT h FROM HistorialServicio h WHERE h.calificacionServicio >= :calificacionMinima")
    List<HistorialServicio> findServiciosConBuenaCalificacion(
            @Param("calificacionMinima") Integer calificacionMinima
    );

    @Query("SELECT h FROM HistorialServicio h WHERE h.completadoALas BETWEEN :inicio AND :fin")
    List<HistorialServicio> findHistorialPorRangoFechas(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}
