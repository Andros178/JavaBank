package com.example.bank.transaccion.services;

import com.example.bank.cuenta.Cuenta;
import com.example.bank.cuenta.services.CuentaService;
import com.example.bank.tipoTransaccion.TipoTransaccion;
import com.example.bank.tipoTransaccion.services.TipoTransaccionService;
import com.example.bank.transaccion.Transaccion;
import com.example.bank.transaccion.dtos.TransaccionCreateDTO;
import com.example.bank.transaccion.repositories.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaService cuentaService;
    private final TipoTransaccionService tipoTransaccionService;

    @Transactional
    public Transaccion createTransaccion(TransaccionCreateDTO dto) {
        Cuenta cuentaOrigen = cuentaService.getCuentaById(dto.getCuentaOrigenId());
        TipoTransaccion tipoTransaccion = tipoTransaccionService.getTipoTransaccionById(dto.getTipoTransaccionId());
        
        Cuenta cuentaDestino = null;
        if (dto.getCuentaDestinoId() != null) {
            cuentaDestino = cuentaService.getCuentaById(dto.getCuentaDestinoId());
        }

        String tipoNombre = tipoTransaccion.getNombre().toUpperCase();
        
        if ("TRANSFERENCIA".equals(tipoNombre)) {
            if (cuentaDestino == null) {
                throw new IllegalArgumentException("La cuenta destino es obligatoria para transferencias");
            }
            cuentaService.updateSaldo(cuentaOrigen.getId(), dto.getMonto().negate());
            cuentaService.updateSaldo(cuentaDestino.getId(), dto.getMonto());
        } else if ("RETIRO".equals(tipoNombre)) {
            cuentaService.updateSaldo(cuentaOrigen.getId(), dto.getMonto().negate());
        } else if ("CONSIGNACION".equals(tipoNombre)) {
            cuentaService.updateSaldo(cuentaOrigen.getId(), dto.getMonto());
        }

        Transaccion transaccion = Transaccion.builder()
                .cuentaOrigen(cuentaOrigen)
                .cuentaDestino(cuentaDestino)
                .tipoTransaccion(tipoTransaccion)
                .monto(dto.getMonto())
                .descripcion(dto.getDescripcion())
                .build();

        return transaccionRepository.save(transaccion);
    }

    public Transaccion getTransaccionById(Long id) {
        return transaccionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada con ID: " + id));
    }

    public List<Transaccion> getAllTransacciones() {
        return transaccionRepository.findAll();
    }

    public List<Transaccion> getTransaccionesByCuentaOrigen(Long cuentaOrigenId) {
        cuentaService.getCuentaById(cuentaOrigenId);
        return transaccionRepository.findByCuentaOrigenId(cuentaOrigenId);
    }

    public List<Transaccion> getTransaccionesByCuentaDestino(Long cuentaDestinoId) {
        cuentaService.getCuentaById(cuentaDestinoId);
        return transaccionRepository.findByCuentaDestinoId(cuentaDestinoId);
    }

    public List<Transaccion> getTransaccionesByTipo(Long tipoTransaccionId) {
        tipoTransaccionService.getTipoTransaccionById(tipoTransaccionId);
        return transaccionRepository.findByTipoTransaccionId(tipoTransaccionId);
    }

    @Transactional
    public void deleteTransaccion(Long id) {
        Transaccion transaccion = getTransaccionById(id);
        transaccionRepository.delete(transaccion);
    }
}
