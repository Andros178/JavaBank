package com.example.bank.transaccion.repositories;

import com.example.bank.transaccion.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCuentaOrigenId(Long cuentaOrigenId);
    List<Transaccion> findByCuentaDestinoId(Long cuentaDestinoId);
    List<Transaccion> findByTipoTransaccionId(Long tipoTransaccionId);
}
