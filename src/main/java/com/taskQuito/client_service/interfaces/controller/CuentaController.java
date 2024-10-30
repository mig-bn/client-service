package com.taskQuito.client_service.interfaces.controller;

import com.taskQuito.client_service.application.service.CuentaService;
import com.taskQuito.client_service.interfaces.dto.CuentaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<List<CuentaDto>> listarCuentas() {
        List<CuentaDto> cuentas = cuentaService.listarCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaDto> obtenerCuenta(@PathVariable String numeroCuenta) {
        CuentaDto cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping
    public ResponseEntity<CuentaDto> crearCuenta(@RequestBody CuentaDto cuentaDto) {
        CuentaDto cuenta = cuentaService.crearCuenta(cuentaDto);
        return ResponseEntity.status(201).body(cuenta);
    }

}