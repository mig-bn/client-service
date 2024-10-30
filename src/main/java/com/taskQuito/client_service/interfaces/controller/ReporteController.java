package com.taskQuito.client_service.interfaces.controller;

import com.taskQuito.client_service.application.service.CuentaService;
import com.taskQuito.client_service.interfaces.dto.ReporteMovimientoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/fecha={rangoFechas}")
    public ResponseEntity<List<ReporteMovimientoDto>> generarReporteMovimientos(
            @PathVariable String rangoFechas) {

        // Dividir el rango en fechaInicio y fechaFin
        String[] fechas = rangoFechas.split(",");
        LocalDate fechaInicio = LocalDate.parse(fechas[0]);
        LocalDate fechaFin = LocalDate.parse(fechas[1]);

        List<ReporteMovimientoDto> reporte = cuentaService.generarReporteMovimientos(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
