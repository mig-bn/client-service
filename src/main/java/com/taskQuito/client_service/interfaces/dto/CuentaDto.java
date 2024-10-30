package com.taskQuito.client_service.interfaces.dto;

import lombok.Data;

@Data
public class CuentaDto {
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;
    private String cliente;
}
