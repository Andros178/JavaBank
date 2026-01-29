package com.example.bank.tipoTransaccion.repositories;

import com.example.bank.tipoTransaccion.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Long> {
    Optional<TipoTransaccion> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
