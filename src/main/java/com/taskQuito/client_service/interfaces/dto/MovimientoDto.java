package com.taskQuito.client_service.interfaces.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimientoDto {

    private Long id;
    private LocalDateTime fecha;
    private String tipoMovimiento; // "depositar" o "retirar"
    private double monto;
    private double saldo;
    private String numeroCuenta; // ID de la cuenta
    private String cliente;
}