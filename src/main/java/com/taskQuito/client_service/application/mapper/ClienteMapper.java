package com.taskQuito.client_service.application.mapper;

import com.taskQuito.client_service.infrastructure.persistence.entity.ClienteEntity;
import com.taskQuito.client_service.interfaces.dto.ClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    @Autowired
    private PersonaMapper personaMapper;

    public ClienteDto toDto(ClienteEntity clienteEntity) {
        ClienteDto dto = new ClienteDto();
        dto.setId(clienteEntity.getId());
        dto.setCliente(clienteEntity.getClienteId());
        dto.setContrasena(clienteEntity.getContrasena());
        dto.setEstado(clienteEntity.isEstado());

        if (clienteEntity.getPersona() != null) {
            dto.setPersona(personaMapper.toDto(clienteEntity.getPersona()));
        }

        return dto;
    }

    public ClienteEntity toEntity(ClienteDto clienteDto) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(clienteDto.getId());
        entity.setClienteId(clienteDto.getCliente());
        entity.setContrasena(clienteDto.getContrasena());
        entity.setEstado(clienteDto.isEstado());

        if (clienteDto.getPersona() != null) {
            entity.setPersona(personaMapper.toEntity(clienteDto.getPersona()));
        }

        return entity;
    }
}
