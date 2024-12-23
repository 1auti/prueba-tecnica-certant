package com.lautaro_cenizo.entity.tecnico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
    List<Tecnico> findByEspecialidadAndEstaActivo(Especialidad especialidad, Boolean estaActivo);
    Optional<Tecnico> findByCardId(Long cardId);
    List<Tecnico> findByEstaActivo(Boolean estaActivo);

    @Query("SELECT t FROM Tecnico t WHERE t.especialidad = :especialidad AND t.estaActivo = true")
    List<Tecnico> findTecnicosDisponiblesPorEspecialidad(@Param("especialidad") Especialidad especialidad);
}
