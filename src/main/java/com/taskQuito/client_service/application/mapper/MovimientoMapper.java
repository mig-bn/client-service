package com.taskQuito.client_service.application.mapper;

import com.taskQuito.client_service.application.service.CuentaService;
import com.taskQuito.client_service.infrastructure.persistence.entity.CuentaEntity;
import com.taskQuito.client_service.infrastructure.persistence.entity.MovimientoEntity;
import com.taskQuito.client_service.interfaces.dto.MovimientoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

@Component
public class MovimientoMapper {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaMapper cuentaMapper;

    public MovimientoDto toDto(MovimientoEntity movimientoEntity) {
        MovimientoDto dto = new MovimientoDto();
        dto.setId(movimientoEntity.getId());
        dto.setFecha(movimientoEntity.getFecha());
        dto.setTipoMovimiento(movimientoEntity.getTipoMovimiento());
        dto.setMonto(movimientoEntity.getMonto());
        dto.setSaldo(movimientoEntity.getSaldo());

        if (movimientoEntity.getCuenta() != null) {
            dto.setNumeroCuenta(movimientoEntity.getCuenta().getNumeroCuenta());

            if (movimientoEntity.getCuenta().getCliente() != null) {
                dto.setCliente(movimientoEntity.getCuenta().getCliente().getClienteId()); // Suponiendo que clienteId es el identificador
            }
        }

        return dto;
    }

    public MovimientoEntity toEntity(MovimientoDto movimientoDto) {
        MovimientoEntity entity = new MovimientoEntity();
        entity.setId(movimientoDto.getId());
        entity.setFecha(movimientoDto.getFecha());
        entity.setTipoMovimiento(movimientoDto.getTipoMovimiento());
        entity.setMonto(movimientoDto.getMonto());
        entity.setSaldo(movimientoDto.getSaldo());

        // Obtener el CuentaDto y convertirlo a CuentaEntity antes de asignarlo
        if (movimientoDto.getNumeroCuenta() != null) {
            CuentaEntity cuentaEntity = cuentaMapper.toEntity(cuentaService.getCuentaByNumeroCuenta(movimientoDto.getNumeroCuenta()));
            entity.setCuenta(cuentaEntity);
        }
        return entity;
    }
}