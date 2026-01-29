package com.example.bank.cliente;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.bank.tipoIdentificacion.TipoIdentificacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cliente", schema="public")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_generator")
    @SequenceGenerator(name="cliente_generator", sequenceName= "cliente_cli_id_seq", allocationSize = 1)
    @Column(name="cli_id", nullable=false)
    private Long id;

    @Column(name="cli_numero_identificacion", nullable=false, unique=true, length=24)
    private String numeroIdentificacion;

    @Column(name="cli_primer_nombre", nullable=false, length=100)
    private String primerNombre;

    @Column(name = "cli_segundo_nombre", length = 100)
    private String segundoNombre;

    @Column(name = "cli_primer_apellido", nullable = false, length = 100)
    private String primerApellido;

    @Column(name = "cli_segundo_apellido", length = 100)
    private String segundoApellido;

    @Column(name = "cli_correo", nullable = false, length = 100, unique = true)
    private String correo;

    @Column(name = "cli_fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "cli_fecha_creacion", nullable = false, updatable=false)
    private LocalDateTime fechaCreacion;

    @Column(name = "cli_fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "cli_tipo_identificacion", referencedColumnName = "tii_id", nullable = false)
    private TipoIdentificacion tipoIdentificacion;

     @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

}
