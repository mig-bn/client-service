package com.taskQuito.client_service.application.service;

import com.taskQuito.client_service.application.exceptions.HttpGenericException;
import com.taskQuito.client_service.application.mapper.ClienteMapper;
import com.taskQuito.client_service.application.mapper.CuentaMapper;
import com.taskQuito.client_service.infrastructure.persistence.crud.CuentaCrudRepository;
import com.taskQuito.client_service.infrastructure.persistence.entity.ClienteEntity;
import com.taskQuito.client_service.interfaces.dto.*;
import com.taskQuito.client_service.infrastructure.persistence.entity.CuentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaCrudRepository cuentaCrudRepository;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    @Lazy
    private MovimientoService movimientoService;

    @Autowired
    public CuentaService(CuentaCrudRepository cuentaCrudRepository, CuentaMapper cuentaMapper) {
        this.cuentaCrudRepository = cuentaCrudRepository;
        this.cuentaMapper = cuentaMapper;
    }

    public List<CuentaDto> listarCuentas() {
        try {
            List<CuentaEntity> cuentas = cuentaCrudRepository.findAll();

            if (cuentas.isEmpty()) {
                throw new HttpGenericException(HttpStatus.NOT_FOUND, "No se encontraron cuentas.");
            }

            return cuentas.stream()
                    .map(cuentaMapper::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al listar cuentas.", e);
        }
    }

    public CuentaDto obtenerCuentaPorNumero(String numeroCuenta) {
        try {
            CuentaEntity cuentaEntity = cuentaCrudRepository.findById(numeroCuenta)
                    .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND, "Cuenta no encontrada con número: " + numeroCuenta));

            return cuentaMapper.toDto(cuentaEntity);

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al obtener cuenta.", e);
        }
    }

    public CuentaDto crearCuenta(CuentaDto cuentaDto) {
        // Obtener el cliente por clienteId y verificar que existe
        ClienteDto clienteDto = clienteService.getClienteByClienteId(cuentaDto.getCliente());
        if (clienteDto == null) {
            throw new HttpGenericException(HttpStatus.NOT_FOUND, "No se puede crear la cuenta. Cliente no encontrado con ID: " + cuentaDto.getCliente());
        }

        if (cuentaCrudRepository.findByNumeroCuenta(cuentaDto.getNumeroCuenta()).isPresent()) {
            throw new HttpGenericException(HttpStatus.BAD_REQUEST, "El número de cuenta ya existe: " + cuentaDto.getNumeroCuenta());
        }

        // Crear y asociar CuentaEntity con el ID del cliente existente
        ClienteEntity clienteEntity = clienteMapper.toEntity(clienteDto);

        CuentaEntity cuentaEntity = new CuentaEntity();
        cuentaEntity.setNumeroCuenta(cuentaDto.getNumeroCuenta());
        cuentaEntity.setTipoCuenta(cuentaDto.getTipoCuenta());
        cuentaEntity.setSaldoInicial(cuentaDto.getSaldoInicial());
        cuentaEntity.setEstado(cuentaDto.isEstado());
        cuentaEntity.setCliente(clienteEntity);

        // Guardar la cuenta
        CuentaEntity cuentaGuardada = cuentaCrudRepository.save(cuentaEntity);

        // Convertir la cuenta guardada a CuentaDto para la respuesta
        cuentaDto.setId(cuentaGuardada.getId());
        return cuentaDto;
    }

    public CuentaDto getCuentaById(Long cuentaId) {
        String cuentaIdString = cuentaId.toString();

        CuentaEntity cuentaEntity = cuentaCrudRepository.findById(cuentaIdString)
                .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND, "Cuenta no encontrada con ID: " + cuentaId));

        return cuentaMapper.toDto(cuentaEntity);
    }

    public void actualizarCuenta(CuentaEntity cuentaEntity) {
        cuentaCrudRepository.save(cuentaEntity);
    }

    public CuentaDto getCuentaByNumeroCuenta(String numeroCuenta) {
        CuentaEntity cuentaEntity = cuentaCrudRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND, "Cuenta no encontrada con el número de cuenta: " + numeroCuenta));

        return cuentaMapper.toDto(cuentaEntity);
    }

    public CuentaDto getCuentaByNumeroCuenta(String numeroCuenta, Long clienteId) {
        CuentaEntity cuentaEntity = cuentaCrudRepository.findByNumeroCuentaAndClienteId(numeroCuenta, clienteId)
                .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND,
                        "Cuenta no encontrada con el número de cuenta: " + numeroCuenta + " y cliente ID: " + clienteId));

        return cuentaMapper.toDto(cuentaEntity);
    }

    public List<ReporteMovimientoDto> generarReporteMovimientos(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteMovimientoDto> reporte = new ArrayList<>();

        LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay();
        LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59);

        // Obtener todas las cuentas
        List<CuentaEntity> cuentas = cuentaCrudRepository.findAll();

        for (CuentaEntity cuenta : cuentas) {
            // Obtener los movimientos dentro del rango y ordenar para obtener el más reciente
            List<MovimientoDto> movimientos = movimientoService.obtenerMovimientosPorCuentaYRangoFecha(cuenta.getId(), fechaInicioDateTime, fechaFinDateTime);

            // Filtrar el último movimiento (si existen movimientos en el rango)
            movimientos.stream()
                    .max(Comparator.comparing(MovimientoDto::getFecha)) // Obtener el movimiento más reciente
                    .ifPresent(movimiento -> {
                        ReporteMovimientoDto reporteDto = new ReporteMovimientoDto();
                        reporteDto.setFecha(movimiento.getFecha().toLocalDate().toString());
                        reporteDto.setCliente(cuenta.getCliente().getPersona().getNombre()); // Ajusta para obtener el nombre del cliente
                        reporteDto.setNumeroCuenta(cuenta.getNumeroCuenta());
                        reporteDto.setTipo(cuenta.getTipoCuenta());
                        reporteDto.setSaldo(cuenta.getSaldoInicial());
                        reporteDto.setEstado(cuenta.isEstado());
                        reporteDto.setUltimotipoMovimiento(movimiento.getTipoMovimiento());
                        reporteDto.setUltimoMovimiento(movimiento.getMonto());
                        reporteDto.setSaldoDisponible(movimiento.getSaldo());

                        reporte.add(reporteDto);
                    });
        }

        return reporte;
    }
}
