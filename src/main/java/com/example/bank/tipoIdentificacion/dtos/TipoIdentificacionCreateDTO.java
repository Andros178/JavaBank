package com.example.bank.tipoIdentificacion.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoIdentificacionCreateDTO {
    
    @NotBlank(message = "El nombre del tipo de identificación es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "La descripción del tipo de identificacion es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder 100 caracteres")
    private String descripcion;


}
