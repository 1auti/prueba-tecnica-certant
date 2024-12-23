package com.lautaro_cenizo.entity.cita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByClienteId(Integer clienteId);
    List<Cita> findByVehiculoId(Integer vehiculoId);
    List<Cita> findByProceso(ESTADO estado);

    @Query("SELECT c FROM Cita c WHERE c.fecha BETWEEN :inicio AND :fin")
    List<Cita> findCitasEntreFechas(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT c FROM Cita c WHERE c.fecha > :fecha AND c.proceso = 'PENDIENTE'")
    List<Cita> findCitasFuturas(LocalDateTime fecha);
    List<Cita> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Cita> findByFechaBetweenAndProceso(LocalDateTime inicio, LocalDateTime fin, ESTADO estado);


    @Query("SELECT COUNT(c) FROM Cita c WHERE c.fecha = :fecha")
    long countCitasByFecha(@Param("fecha") LocalDateTime fecha);
}
