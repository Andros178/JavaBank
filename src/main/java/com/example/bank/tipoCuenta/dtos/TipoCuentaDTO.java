package com.example.bank.tipoCuenta.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoCuentaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long estadoId;
}
