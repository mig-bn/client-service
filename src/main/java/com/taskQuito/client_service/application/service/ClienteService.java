package com.taskQuito.client_service.application.service;

import com.taskQuito.client_service.application.exceptions.HttpGenericException;
import com.taskQuito.client_service.application.mapper.ClienteMapper;
import com.taskQuito.client_service.application.mapper.PersonaMapper;
import com.taskQuito.client_service.infrastructure.persistence.crud.ClienteCrudRepository;
import com.taskQuito.client_service.interfaces.dto.ClienteDto;
import com.taskQuito.client_service.interfaces.dto.PersonaDto;
import com.taskQuito.client_service.infrastructure.persistence.entity.ClienteEntity;
import com.taskQuito.client_service.infrastructure.persistence.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteCrudRepository clienteCrudRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    public ClienteService(ClienteCrudRepository clienteCrudRepository, ClienteMapper clienteMapper,
                          PersonaMapper personaMapper) {
        this.clienteCrudRepository = clienteCrudRepository;
        this.clienteMapper = clienteMapper;
        this.personaMapper = personaMapper;
    }

    public List<ClienteDto> listClientes() {
        try {
            List<ClienteEntity> clientes = clienteCrudRepository.findAll();

            if (clientes.isEmpty()) {
                throw new HttpGenericException(HttpStatus.NOT_FOUND, "No se encontraron clientes.");
            }

            return clientes.stream()
                    .map(clienteMapper::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al listar clientes.", e);
        }
    }

    public ClienteDto obtenerClientePorId(Long id) {
        try {
            ClienteEntity clienteEntity = clienteCrudRepository.findById(id)
                    .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id));

            return clienteMapper.toDto(clienteEntity);

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al obtener cliente.", e);
        }
    }

    public ClienteDto crearCliente(ClienteDto clienteDto) {
        try {
            // Convertir ClienteDto a ClienteEntity
            ClienteEntity clienteEntity = clienteMapper.toEntity(clienteDto);

            // Si hay datos de persona en ClienteDto, asignar a ClienteEntity
            if (clienteDto.getPersona() != null) {
                PersonaEntity personaEntity = personaMapper.toEntity(clienteDto.getPersona());
                clienteEntity.setPersona(personaEntity);
            }

            // Guardar ClienteEntity (con PersonaEntity si está presente)
            ClienteEntity clienteGuardado = clienteCrudRepository.save(clienteEntity);

            // Convertir el cliente guardado a ClienteDto para retornar el resultado
            return clienteMapper.toDto(clienteGuardado);

        } catch (Exception e) {
            throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al crear cliente.", e);
        }
    }

    public ClienteDto getClienteByClienteId(String clienteId) {
        ClienteEntity clienteEntity = clienteCrudRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new HttpGenericException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + clienteId));

        return clienteMapper.toDto(clienteEntity);
    }
}