package com.example.bank.tipoCuenta.repositories;

import com.example.bank.tipoCuenta.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoCuentaRepository extends JpaRepository<TipoCuenta, Long> {
    Optional<TipoCuenta> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
