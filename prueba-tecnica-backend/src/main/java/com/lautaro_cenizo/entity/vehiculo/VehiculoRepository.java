package com.lautaro_cenizo.entity.vehiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    Optional<Vehiculo> findByPatente(String patente);
    List<Vehiculo> findByClienteId(Integer clienteId);
    List<Vehiculo> findByMarcaAndModelo(String marca, String modelo);
    boolean existsByPatente(String patente);
}
