package com.example.bank.cliente.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteCreateDTO {
    
    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(max = 24, message = "El número de identificación no puede exceder 24 caracteres")
    private String numeroIdentificacion;
    
    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 100, message = "El primer nombre no puede exceder 100 caracteres")
    private String primerNombre;
    
    @Size(max = 100, message = "El segundo nombre no puede exceder 100 caracteres")
    private String segundoNombre;
    
    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 100, message = "El primer apellido no puede exceder 100 caracteres")
    private String primerApellido;
    
    @Size(max = 100, message = "El segundo apellido no puede exceder 100 caracteres")
    private String segundoApellido;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correo;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;
    
    @NotNull(message = "El tipo de identificación es obligatorio")
    private Long tipoIdentificacionId;
}
