package com.lautaro_cenizo.entity.repuesto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestoRepository extends JpaRepository<Repuesto,Integer> {

    //Sirve para identificar respuestos de niveles de inventario bajos y Para generar alertas automáticas o realizar pedidos de reposición.
    List<Repuesto> findByStockLessThanEqual(Integer stockMinimo);
}
