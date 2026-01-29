package com.example.bank.cuenta.services;

import com.example.bank.cliente.Cliente;
import com.example.bank.cliente.services.ClienteService;
import com.example.bank.cuenta.Cuenta;
import com.example.bank.cuenta.dtos.CuentaCreateDTO;
import com.example.bank.cuenta.repositories.CuentaRepository;
import com.example.bank.estado.Estado;
import com.example.bank.estado.services.EstadoService;
import com.example.bank.tipoCuenta.TipoCuenta;
import com.example.bank.tipoCuenta.services.TipoCuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;
    private final TipoCuentaService tipoCuentaService;
    private final EstadoService estadoService;

    private static final String ESTADO_DEFAULT = "Activo";

    @Transactional
    public Cuenta createCuenta(CuentaCreateDTO dto) {
        if (cuentaRepository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new IllegalArgumentException("Ya existe una cuenta con este número");
        }

        Cliente cliente = clienteService.getClienteById(dto.getClienteId());
        TipoCuenta tipoCuenta = tipoCuentaService.getTipoCuentaById(dto.getTipoCuentaId());
        Estado estado = estadoService.getEstadoByNombre(ESTADO_DEFAULT);

        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(dto.getNumeroCuenta())
                .saldo(dto.getSaldo())
                .saldoDisponible(dto.getSaldo())
                .excentaGMF(dto.getExcentaGMF())
                .cliente(cliente)
                .tipoCuenta(tipoCuenta)
                .estado(estado)
                .build();

        return cuentaRepository.save(cuenta);
    }

    public Cuenta getCuentaById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con ID: " + id));
    }

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta getCuentaByNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con número: " + numeroCuenta));
    }

    public List<Cuenta> getCuentasByClienteId(Long clienteId) {
        clienteService.getClienteById(clienteId);
        return cuentaRepository.findByClienteId(clienteId);
    }

    @Transactional
    public Cuenta activateCuenta(Long id) {
        Cuenta cuenta = getCuentaById(id);
        Estado estadoActivo = estadoService.getEstadoByNombre(ESTADO_DEFAULT);
        cuenta.setEstado(estadoActivo);
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Cuenta inactivateCuenta(Long id) {
        Cuenta cuenta = getCuentaById(id);
        Estado estadoInactivo = estadoService.getEstadoByNombre("Inactivo");
        cuenta.setEstado(estadoInactivo);
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void cancelarCuenta(Long id) {
        Cuenta cuenta = getCuentaById(id);
        if (cuenta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("No se puede cancelar la cuenta porque el saldo no es cero");
        }
        Estado estadoCancelado = estadoService.getEstadoByNombre("Cancelado");
        cuenta.setEstado(estadoCancelado);
        cuentaRepository.save(cuenta);
    }

    @Transactional
    public void deleteCuenta(Long id) {
        Cuenta cuenta = getCuentaById(id);
        cuentaRepository.delete(cuenta);
    }

    @Transactional
    public Cuenta updateSaldo(Long id, BigDecimal monto) {
        Cuenta cuenta = getCuentaById(id);
        
        BigDecimal nuevoSaldo = cuenta.getSaldo().add(monto);
        
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cuenta de ahorros no puede tener saldo negativo");
        }

        cuenta.setSaldo(nuevoSaldo);
        cuenta.setSaldoDisponible(nuevoSaldo);
        
        return cuentaRepository.save(cuenta);
    }
}
