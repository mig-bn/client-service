package com.taskQuito.client_service.application.mapper;

import com.taskQuito.client_service.infrastructure.persistence.entity.PersonaEntity;
import com.taskQuito.client_service.interfaces.dto.PersonaDto;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    public PersonaDto toDto(PersonaEntity personaEntity) {
        PersonaDto dto = new PersonaDto();
        dto.setId(personaEntity.getId());
        dto.setNombre(personaEntity.getNombre());
        dto.setGenero(personaEntity.getGenero());
        dto.setEdad(personaEntity.getEdad());
        dto.setIdentificacion(personaEntity.getIdentificacion());
        dto.setDireccion(personaEntity.getDireccion());
        dto.setTelefono(personaEntity.getTelefono());
        return dto;
    }

    public PersonaEntity toEntity(PersonaDto personaDto) {
        PersonaEntity entity = new PersonaEntity();
        entity.setId(personaDto.getId());
        entity.setNombre(personaDto.getNombre());
        entity.setGenero(personaDto.getGenero());
        entity.setEdad(personaDto.getEdad());
        entity.setIdentificacion(personaDto.getIdentificacion());
        entity.setDireccion(personaDto.getDireccion());
        entity.setTelefono(personaDto.getTelefono());
        return entity;
    }

}
