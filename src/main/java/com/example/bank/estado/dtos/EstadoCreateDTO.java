package com.example.bank.estado.dtos;

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
public class EstadoCreateDTO {
    
    @NotBlank(message = "El nombre del estado es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "La descripción del estado es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder 100 caracteres")
    private String descripcion;
}
