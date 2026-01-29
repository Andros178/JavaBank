package com.example.bank.tipoTransaccion.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoTransaccionDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}
