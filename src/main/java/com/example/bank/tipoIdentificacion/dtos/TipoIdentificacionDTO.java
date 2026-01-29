package com.example.bank.tipoIdentificacion.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoIdentificacionDTO {
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

}
