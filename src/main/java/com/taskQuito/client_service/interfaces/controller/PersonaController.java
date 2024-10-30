package com.taskQuito.client_service.interfaces.controller;

import com.taskQuito.client_service.application.service.PersonaService;
import com.taskQuito.client_service.interfaces.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<PersonaDto>> listarPersonas() {
        List<PersonaDto> personas = personaService.listarPersonas();
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDto> obtenerPersona(@PathVariable Long id) {
        PersonaDto persona = personaService.obtenerPersonaPorId(id);
        return ResponseEntity.ok(persona);
    }

    @PostMapping
    public ResponseEntity<PersonaDto> crearPersona(@RequestBody PersonaDto personaDto) {
        PersonaDto persona = personaService.crearPersona(personaDto);
        return ResponseEntity.status(201).body(persona);
    }
}
