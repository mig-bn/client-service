package com.taskQuito.client_service.application.mapper;

import com.taskQuito.client_service.application.service.ClienteService;
import com.taskQuito.client_service.infrastructure.persistence.entity.ClienteEntity;
import com.taskQuito.client_service.infrastructure.persistence.entity.CuentaEntity;
import com.taskQuito.client_service.interfaces.dto.CuentaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    public CuentaDto toDto(CuentaEntity cuentaEntity) {
        CuentaDto dto = new CuentaDto();
        dto.setId(cuentaEntity.getId());
        dto.setNumeroCuenta(cuentaEntity.getNumeroCuenta());
        dto.setTipoCuenta(cuentaEntity.getTipoCuenta());
        dto.setSaldoInicial(cuentaEntity.getSaldoInicial());
        dto.setEstado(cuentaEntity.isEstado());

        // Verificar si cuentaEntity tiene un cliente asociado y asignar su ID al DTO
        if (cuentaEntity.getCliente() != null) {
            dto.setCliente(cuentaEntity.getCliente().getClienteId());
        }

        return dto;
    }

    public CuentaEntity toEntity(CuentaDto cuentaDto) {
        CuentaEntity entity = new CuentaEntity();
        entity.setId(cuentaDto.getId());
        entity.setNumeroCuenta(cuentaDto.getNumeroCuenta());
        entity.setTipoCuenta(cuentaDto.getTipoCuenta());
        entity.setSaldoInicial(cuentaDto.getSaldoInicial());
        entity.setEstado(cuentaDto.isEstado());

        // Obtener el cliente asociado usando clienteId en CuentaDto si no es nulo
        if (cuentaDto.getCliente() != null) {
            ClienteEntity clienteEntity = clienteMapper.toEntity(clienteService.getClienteByClienteId(cuentaDto.getCliente()));
            entity.setCliente(clienteEntity);
        }

        return entity;
    }
}
