package com.taskQuito.client_service.application.service;

import com.taskQuito.client_service.application.exceptions.HttpGenericException;
import com.taskQuito.client_service.application.mapper.ClienteMapper;
import com.taskQuito.client_service.application.mapper.CuentaMapper;
import com.taskQuito.client_service.application.mapper.MovimientoMapper;
import com.taskQuito.client_service.infrastructure.persistence.crud.MovimientoCrudRepository;
import com.taskQuito.client_service.infrastructure.persistence.entity.CuentaEntity;
import com.taskQuito.client_service.interfaces.dto.ClienteDto;
import com.taskQuito.client_service.interfaces.dto.CuentaDto;
import com.taskQuito.client_service.interfaces.dto.MovimientoDto;
import com.taskQuito.client_service.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoCrudRepository movimientoCrudRepository;

    @Autowired
    private MovimientoMapper movimientoMapper;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired

    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    public MovimientoService(MovimientoCrudRepository movimientoCrudRepository, MovimientoMapper movimientoMapper) {
        this.movimientoCrudRepository = movimientoCrudRepository;
        this.movimientoMapper = movimientoMapper;
    }

    public List<MovimientoDto> listarMovimientos() {
        try {
            List<MovimientoEntity> movimientos = movimientoCrudRepository.findAll();

            if (movimientos.isEmpty()) {
                throw new HttpGenericException(HttpStatus.NOT_FOUND, "No se encontraron movimientos.");
            }

            return movimientos.stream()
                    .map(movimientoMapper::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al listar movimientos.", e);
        }
    }

    public MovimientoDto obtenerMovimientoPorId(Long id) {
        try {
            MovimientoEntity movimientoEntity = movimientoCrudRepository.findById(id)
                    .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND, "Movimiento no encontrado con ID: " + id));

            return movimientoMapper.toDto(movimientoEntity);

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al obtener movimiento.", e);
        }
    }

    public MovimientoDto realizarMovimiento(MovimientoDto movimientoDto) {
        // Validar si el cliente existe usando el identificador único clienteId (String)
        ClienteDto clienteDto = clienteService.getClienteByClienteId(movimientoDto.getCliente());
        if (clienteDto == null) {
            throw new HttpGenericException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + movimientoDto.getCliente());
        }

        // Validar si la cuenta existe y pertenece al cliente con el clienteId especificado
        CuentaDto cuentaDto = cuentaService.getCuentaByNumeroCuenta(movimientoDto.getNumeroCuenta(), clienteDto.getId());
        if (cuentaDto == null) {
            throw new HttpGenericException(HttpStatus.NOT_FOUND, "Cuenta no encontrada con Numero: " + movimientoDto.getNumeroCuenta());
        }

        // Convertir CuentaDto a CuentaEntity para actualizar el saldo
        CuentaEntity cuentaEntity = cuentaMapper.toEntity(cuentaDto);
        movimientoDto.setFecha(LocalDateTime.now());

        // Validar tipo de movimiento y ajustar el saldo
        if ("retirar".equalsIgnoreCase(movimientoDto.getTipoMovimiento())) {
            if (cuentaEntity.getSaldoInicial() < movimientoDto.getMonto()) {
                throw new HttpGenericException(HttpStatus.BAD_REQUEST, "Saldo no disponible");
            }
            cuentaEntity.setSaldoInicial(cuentaEntity.getSaldoInicial() - movimientoDto.getMonto());
        } else if ("depositar".equalsIgnoreCase(movimientoDto.getTipoMovimiento())) {
            cuentaEntity.setSaldoInicial(cuentaEntity.getSaldoInicial() + movimientoDto.getMonto());
        } else {
            throw new HttpGenericException(HttpStatus.BAD_REQUEST, "Tipo de movimiento no válido. Use 'depositar' o 'retirar'");
        }

        // Crear y asociar MovimientoEntity con cuenta y cliente
        MovimientoEntity movimientoEntity = movimientoMapper.toEntity(movimientoDto);
        movimientoEntity.setCuenta(cuentaEntity);
        movimientoEntity.setCliente(clienteMapper.toEntity(clienteDto));
        movimientoEntity.setSaldo(cuentaEntity.getSaldoInicial());

        // Guardar el movimiento y actualizar el saldo de la cuenta
        MovimientoEntity movimientoGuardado = movimientoCrudRepository.save(movimientoEntity);
        cuentaService.actualizarCuenta(cuentaEntity);

        return movimientoMapper.toDto(movimientoGuardado);
    }

    public List<MovimientoDto> obtenerMovimientosPorCuentaYRangoFecha(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<MovimientoEntity> movimientos = movimientoCrudRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaInicio, fechaFin);
        return movimientos.stream().map(movimientoMapper::toDto).collect(Collectors.toList());
    }
}