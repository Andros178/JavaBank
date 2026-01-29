package com.example.bank.tipoTransaccion;

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
@Table(name="tipo_transaccion", schema="public")
public class TipoTransaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_transaccion_generator")
    @SequenceGenerator(name="tipo_transaccion_generator", sequenceName= "tipo_transaccion_tit_id_seq", allocationSize = 1)
    @Column(name="tit_id", nullable=false)
    private Long id;

    @Column(name = "tit_nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "tit_desscripcion", nullable = false, length = 100)
    private String descripcion;
}
