package com.taskQuito.client_service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class MovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private String tipoMovimiento;
    private double monto;
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", referencedColumnName = "id")
    private CuentaEntity cuenta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteEntity cliente;
}
