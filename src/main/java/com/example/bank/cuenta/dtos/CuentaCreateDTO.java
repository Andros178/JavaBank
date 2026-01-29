package com.example.bank.cuenta.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaCreateDTO {
    
    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(max = 10, message = "El número de cuenta no puede exceder 10 caracteres")
    private String numeroCuenta;
    
    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    private BigDecimal saldo;
    
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private Long tipoCuentaId;
    
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;
    
    @NotNull(message = "La exención de GMF es obligatoria")
    private Boolean excentaGMF;
}
