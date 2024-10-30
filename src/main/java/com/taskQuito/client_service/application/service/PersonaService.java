package com.taskQuito.client_service.application.service;

import com.taskQuito.client_service.application.exceptions.HttpGenericException;
import com.taskQuito.client_service.application.mapper.PersonaMapper;
import com.taskQuito.client_service.infrastructure.persistence.crud.PersonaCrudRepository;
import com.taskQuito.client_service.interfaces.dto.PersonaDto;
import com.taskQuito.client_service.infrastructure.persistence.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonaService {
    private final PersonaCrudRepository personaCrudRepository;
    private final PersonaMapper personaMapper;

    @Autowired
    public PersonaService(PersonaCrudRepository personaCrudRepository, PersonaMapper personaMapper) {
        this.personaCrudRepository = personaCrudRepository;
        this.personaMapper = personaMapper;
    }

    public List<PersonaDto> listarPersonas() {
        try {
            List<PersonaEntity> personas = personaCrudRepository.findAll();

            if (personas.isEmpty()) {
                throw new HttpGenericException(HttpStatus.NOT_FOUND, "No se encontraron personas.");
            }

            return personas.stream()
                    .map(personaMapper::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al listar personas.", e);
        }
    }

    public PersonaDto obtenerPersonaPorId(Long id) {
        try {
            PersonaEntity personaEntity = personaCrudRepository.findById(id)
                    .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND, "Persona no encontrada con ID: " + id));

            return personaMapper.toDto(personaEntity);

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al obtener persona.", e);
        }
    }

    public PersonaDto crearPersona(PersonaDto personaDto) {
        try {
            PersonaEntity personaEntity = personaMapper.toEntity(personaDto);
            PersonaEntity personaGuardada = personaCrudRepository.save(personaEntity);
            return personaMapper.toDto(personaGuardada);

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al crear persona.", e);
        }
    }

    public PersonaEntity guardarPersona(PersonaEntity personaEntity) {
        return personaCrudRepository.save(personaEntity);
    }
}
