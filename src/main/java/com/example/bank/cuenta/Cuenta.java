package com.example.bank.cuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.bank.cliente.Cliente;
import com.example.bank.estado.Estado;
import com.example.bank.tipoCuenta.TipoCuenta;

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
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cuenta", schema="default")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuenta_generator")
    @SequenceGenerator(name="cuenta_generator", sequenceName= "cuenta_cue_id_seq", allocationSize = 1)
    @Column(name="cue_id", nullable=false)
    private Long id;

    @Column(name = "cue_numero_cuenta", nullable = false, unique = true, length = 10)
    private String numeroCuenta;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name ="cue_saldo", nullable = false )
    private BigDecimal saldo;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "cue_saldo_disponible", nullable = false)
    private BigDecimal saldoDisponible;

    @Column(name = "cue_excentaGMF", nullable = false)
    private Boolean excentaGMF;

    @Column(name = "cue_fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "cue_fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cue_tipo_cuenta_id", referencedColumnName = "tic_id", nullable = false)
    private TipoCuenta tipoCuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cue_cliente_id", referencedColumnName = "cli_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "cue_estado_id", referencedColumnName = "est_id", nullable = false)
    private Estado estado;


    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
        if (saldoDisponible == null) {
            saldoDisponible = saldo;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
