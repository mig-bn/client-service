package com.taskQuito.client_service.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Data
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clienteId;
    private String contrasena;
    private boolean estado;

    @OneToOne(optional = true, cascade = CascadeType.PERSIST) // Usa CascadeType.ALL si necesitas otras operaciones en cascada
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private PersonaEntity persona;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaEntity> cuentas;
}
