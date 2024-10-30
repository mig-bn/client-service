package com.taskQuito.client_service.domain;

import com.taskQuito.client_service.infrastructure.persistence.entity.ClienteEntity;
import com.taskQuito.client_service.infrastructure.persistence.entity.PersonaEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteEntityTest {

    @Test
    public void testClienteWithPersonaProperties() {
        // Crear instancia de Cliente y Persona
        ClienteEntity cliente = new ClienteEntity();
        PersonaEntity persona = new PersonaEntity();

        // Definir valores de prueba para Cliente
        cliente.setClienteId("C123456");
        cliente.setContrasena("juan12345");
        cliente.setEstado(true);

        // Definir valores de prueba para Persona
        persona.setNombre("Juan Pérez");
        persona.setGenero("Masculino");
        persona.setEdad(35);
        persona.setIdentificacion("123456789");
        persona.setDireccion("Calle Falsa 123");
        persona.setTelefono("0987654321");

        // Asignar persona al cliente
        cliente.setPersona(persona);

        // Validar propiedades de Cliente
        assertEquals("C123456", cliente.getClienteId(), "El ID del cliente no coincide.");
        assertEquals("juan12345", cliente.getContrasena(), "La contraseña del cliente no coincide.");
        assertTrue(cliente.isEstado(), "El estado del cliente no coincide.");

        // Validar propiedades de Persona dentro de Cliente
        assertEquals("Juan Pérez 2", cliente.getPersona().getNombre(), "El nombre de la persona no coincide.");
        assertEquals("Masculino 2", cliente.getPersona().getGenero(), "El género de la persona no coincide.");
        assertEquals(35, cliente.getPersona().getEdad(), "La edad de la persona no coincide.");
        assertEquals("123456789", cliente.getPersona().getIdentificacion(), "La identificación de la persona no coincide.");
        assertEquals("Calle Falsa 123", cliente.getPersona().getDireccion(), "La dirección de la persona no coincide.");
    }

    @Test
    public void testModificarClienteConPersona() {
        // Crear instancia de Cliente y Persona
        ClienteEntity cliente = new ClienteEntity();
        PersonaEntity persona = new PersonaEntity();

        // Valores iniciales para Cliente y Persona
        cliente.setClienteId("C123456");
        cliente.setContrasena("juan12345");
        cliente.setEstado(true);

        persona.setNombre("Juan Pérez 2");
        persona.setGenero("Masculino 2");
        persona.setEdad(35);
        persona.setIdentificacion("123456789");
        persona.setDireccion("Calle Falsa 123");
        persona.setTelefono("0987654321");

        // Asignar persona al cliente
        cliente.setPersona(persona);

        // Modificar valores del Cliente y de la Persona
        cliente.setContrasena("nuevaContraseña");
        persona.setNombre("Juan Pérez Modificado");
        persona.setEdad(36);

        // Validaciones de modificación
        assertEquals("nuevaContraseña", cliente.getContrasena());
        assertEquals("Juan Pérez Modificado", cliente.getPersona().getNombre());
        assertEquals(36, cliente.getPersona().getEdad());
    }

    @Test
    public void testEliminarClienteConPersona() {
        // Crear instancia de Cliente y Persona
        ClienteEntity cliente = new ClienteEntity();
        PersonaEntity persona = new PersonaEntity();

        // Definir valores de prueba y asignarlos
        cliente.setClienteId("C123456");
        cliente.setContrasena("juan12345");
        cliente.setEstado(true);
        cliente.setPersona(persona);

        // Simular eliminación
        cliente.setPersona(null); // Disociar la persona
        cliente = null; // Eliminar referencia de cliente

        // Validación de eliminación
        assertNull(cliente, "El cliente debe ser null después de la eliminación.");
    }
}