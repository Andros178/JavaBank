package com.example.bank.cuenta.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaDTO {
    private Long id;
    private String numeroCuenta;
    private BigDecimal saldo;
    private BigDecimal saldoDisponible;
    private Boolean excentaGMF;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long tipoCuentaId;
    private Long clienteId;
    private Long estadoId;
}
