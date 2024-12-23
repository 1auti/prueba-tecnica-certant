package com.lautaro_cenizo.entity.seguimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoRepository extends JpaRepository<SeguimientoServicio,Integer> {
}
