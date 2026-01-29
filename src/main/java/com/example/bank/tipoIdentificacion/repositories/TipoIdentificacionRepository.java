package com.example.bank.tipoIdentificacion.repositories;

import com.example.bank.tipoIdentificacion.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoIdentificacionRepository extends JpaRepository<TipoIdentificacion, Long> {
    Optional<TipoIdentificacion> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
