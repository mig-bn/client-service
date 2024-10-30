package com.taskQuito.client_service.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoModel {
    private Long id;
    private Date fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
}
