package com.example.bank.transaccion.dtos;

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
public class TransaccionDTO {
    private Long id;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
    private Long tipoTransaccionId;
    private BigDecimal monto;
    private String descripcion;
    private LocalDateTime fechaTransaccion;
}
