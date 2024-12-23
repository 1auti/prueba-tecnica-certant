package com.lautaro_cenizo.entity.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByDni(String dni);
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByEsPremuim(Boolean esPremuim);

    @Query("SELECT c FROM Cliente c WHERE c.contadorServicios > :cantidad")
    List<Cliente> findClientesConMasServicios(int cantidad);

    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}