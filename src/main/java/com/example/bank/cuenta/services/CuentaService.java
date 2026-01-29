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
    private static final String TIPO_CUENTA_AHORROS = "Cuenta de ahorros";
    private static final String TIPO_CUENTA_CORRIENTE = "Cuenta corriente";
    private static final String PREFIJO_AHORROS = "53";
    private static final String PREFIJO_CORRIENTE = "33";

    @Transactional
    public Cuenta createCuenta(CuentaCreateDTO dto) {
        Cliente cliente = clienteService.getClienteById(dto.getClienteId());
        TipoCuenta tipoCuenta = tipoCuentaService.getTipoCuentaById(dto.getTipoCuentaId());
        Estado estado = estadoService.getEstadoByNombre(ESTADO_DEFAULT);

        String numeroCuenta = dto.getNumeroCuenta();
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            numeroCuenta = generateNumeroCuenta(tipoCuenta);
        } else {
            if (cuentaRepository.existsByNumeroCuenta(numeroCuenta)) {
                throw new IllegalArgumentException("Ya existe una cuenta con este número");
            }
            validateNumeroCuentaFormat(numeroCuenta, tipoCuenta);
        }

        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(numeroCuenta)
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
        
        String tipoCuentaNombre = cuenta.getTipoCuenta().getNombre();
        if (TIPO_CUENTA_AHORROS.equalsIgnoreCase(tipoCuentaNombre) && 
            nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cuenta de ahorros no puede tener saldo negativo");
        }

        cuenta.setSaldo(nuevoSaldo);
        cuenta.setSaldoDisponible(nuevoSaldo);
        
        return cuentaRepository.save(cuenta);
    }

    private String generateNumeroCuenta(TipoCuenta tipoCuenta) {
        String prefijo = getPrefijoByCuentaTipo(tipoCuenta);
        
        List<Cuenta> ultimasCuentas = cuentaRepository
                .findTopByNumeroCuentaStartingWithOrderByNumeroCuentaDesc(prefijo);
        
        long ultimoNumero = 0;
        if (!ultimasCuentas.isEmpty()) {
            String ultimaCuenta = ultimasCuentas.get(0).getNumeroCuenta();
            try {
                ultimoNumero = Long.parseLong(ultimaCuenta);
            } catch (NumberFormatException e) {
                ultimoNumero = Long.parseLong(prefijo + "00000000");
            }
        } else {
            ultimoNumero = Long.parseLong(prefijo + "00000000");
        }
        
        long nuevoNumero = ultimoNumero + 1;
        return String.format("%010d", nuevoNumero);
    }

    private String getPrefijoByCuentaTipo(TipoCuenta tipoCuenta) {
        String nombreTipo = tipoCuenta.getNombre();
        if (TIPO_CUENTA_AHORROS.equalsIgnoreCase(nombreTipo)) {
            return PREFIJO_AHORROS;
        } else if (TIPO_CUENTA_CORRIENTE.equalsIgnoreCase(nombreTipo)) {
            return PREFIJO_CORRIENTE;
        }
        throw new IllegalArgumentException("Tipo de cuenta no válido: " + nombreTipo);
    }

    private void validateNumeroCuentaFormat(String numeroCuenta, TipoCuenta tipoCuenta) {
        if (numeroCuenta.length() != 10) {
            throw new IllegalArgumentException("El número de cuenta debe tener exactamente 10 dígitos");
        }
        
        if (!numeroCuenta.matches("\\d{10}")) {
            throw new IllegalArgumentException("El número de cuenta debe contener solo dígitos");
        }
        
        String prefijo = getPrefijoByCuentaTipo(tipoCuenta);
        if (!numeroCuenta.startsWith(prefijo)) {
            String tipoCuentaNombre = tipoCuenta.getNombre();
            throw new IllegalArgumentException(
                String.format("El número de cuenta para %s debe empezar con %s", 
                    tipoCuentaNombre, prefijo)
            );
        }
    }
}
