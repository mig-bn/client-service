package com.taskQuito.client_service.interfaces.dto;

import lombok.Data;

@Data
public class ReporteMovimientoDto {
    private String fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private Double saldo;
    private Boolean estado;
    private String UltimotipoMovimiento;
    private Double ultimoMovimiento;
    private Double saldoDisponible;
}
