package com.taskQuito.client_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskQuito.client_service.interfaces.dto.ClienteDto;
import com.taskQuito.client_service.interfaces.dto.PersonaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteCOntrollerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCrearCliente() throws Exception {
        // Crear un objeto ClienteDto con PersonaDto
        PersonaDto personaDto = new PersonaDto();
        personaDto.setNombre("Juan Pérez 2");
        personaDto.setGenero("Masculino 2");
        personaDto.setEdad(35);
        personaDto.setIdentificacion("123456789");
        personaDto.setDireccion("Calle Falsa 123");
        personaDto.setTelefono("0987654321");

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setCliente("C123456");
        clienteDto.setContrasena("juan12345");
        clienteDto.setEstado(true);
        clienteDto.setPersona(personaDto);

        // Convertir ClienteDto a JSON
        String clienteJson = objectMapper.writeValueAsString(clienteDto);

        // Ejecutar la solicitud POST y verificar el resultado
        ResultActions result = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson));

        // Verificar que la respuesta tenga el estado 201 CREATED y los datos esperados
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value("C123456"))
                .andExpect(jsonPath("$.persona.nombre").value("Juan Pérez 2"))
                .andExpect(jsonPath("$.persona.genero").value("Masculino 2"));
    }
}

