package com.taskQuito.client_service.interfaces.dto;

import lombok.Data;

@Data
public class PersonaDto {
    private Long id;
    private String nombre;
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
