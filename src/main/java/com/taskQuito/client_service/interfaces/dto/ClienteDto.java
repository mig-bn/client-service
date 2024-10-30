package com.taskQuito.client_service.interfaces.dto;

import lombok.Data;

@Data
public class ClienteDto {
    private Long id;
    private String cliente;
    private String contrasena;
    private boolean estado;
    private PersonaDto persona;
}
