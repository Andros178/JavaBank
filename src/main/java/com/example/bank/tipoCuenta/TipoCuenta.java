package com.example.bank.tipoCuenta;

import com.example.bank.estado.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="tipo_cuenta", schema="default")
public class TipoCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_cuenta_generator")
    @SequenceGenerator(name="tipo_cuenta_generator", sequenceName= "tipo_cuenta_tic_id_seq", allocationSize = 1)
    @Column(name="tic_id", nullable=false)
    private Long id;

    @Column(name = "tic_nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "tic_descripcion", nullable = false, length = 100)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tic_estado_id", referencedColumnName="est_id", nullable=false)
    private Estado estado;
}
