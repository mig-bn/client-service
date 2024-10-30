package com.taskQuito.client_service.interfaces.controller;

import com.taskQuito.client_service.application.service.MovimientoService;
import com.taskQuito.client_service.interfaces.dto.MovimientoDto;
import com.taskQuito.client_service.interfaces.dto.ReporteMovimientoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDto>> listarMovimientos() {
        List<MovimientoDto> movimientos = movimientoService.listarMovimientos();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDto> obtenerMovimiento(@PathVariable Long id) {
        MovimientoDto movimiento = movimientoService.obtenerMovimientoPorId(id);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/realizar")
    public ResponseEntity<MovimientoDto> realizarMovimiento(@RequestBody MovimientoDto movimientoDto) {
        MovimientoDto movimientoRealizado = movimientoService.realizarMovimiento(movimientoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoRealizado);
    }
}
