package com.taskQuito.client_service.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {
    private String clienteId;
    private String contraseña;
    private boolean estado;
}
