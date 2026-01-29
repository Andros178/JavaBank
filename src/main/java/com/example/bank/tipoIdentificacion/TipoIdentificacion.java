package com.example.bank.tipoIdentificacion;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
@Table(name="tipo_identificacion", schema="default")
public class TipoIdentificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_identificacion_generator")
    @SequenceGenerator(name="tipo_identificacion_generator", sequenceName= "tipo_identificacion_tii_id_seq", allocationSize = 1)
    @Column(name="tii_id", nullable=false)
    private Long id;

    @Column(name = "tii_nombre", nullable = false, unique=true, length = 100)
    private String nombre;

    @Column(name = "tii_descripcion", nullable = false, length = 100)
    private String descripcion;

}
