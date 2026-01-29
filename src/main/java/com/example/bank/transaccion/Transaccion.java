package com.example.bank.transaccion;

import com.example.bank.cuenta.Cuenta;
import com.example.bank.tipoTransaccion.TipoTransaccion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transaccion", schema="public")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaccion_generator")
    @SequenceGenerator(name="transaccion_generator", sequenceName= "transaccion_tra_id_seq", allocationSize = 1)
    
    @Column(name="tra_id", nullable=false)
    private Long id;
    
    @Column(name = "tra_monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "tra_descripcion", length = 500)
    private String descripcion;

    @Column(name = "tra_fecha_transaccion", nullable = false, updatable = false)
    private LocalDateTime fechaTransaccion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tra_cuenta_origen_id", referencedColumnName = "cue_id", nullable = false)
    private Cuenta cuentaOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tra_cuenta_destino_id", referencedColumnName = "cue_id")
    private Cuenta cuentaDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tra_tipo_transaccion_id", referencedColumnName = "tit_id", nullable = false)
    private TipoTransaccion tipoTransaccion;


    @PrePersist
    protected void onCreate() {
        fechaTransaccion = LocalDateTime.now();
    }
}
