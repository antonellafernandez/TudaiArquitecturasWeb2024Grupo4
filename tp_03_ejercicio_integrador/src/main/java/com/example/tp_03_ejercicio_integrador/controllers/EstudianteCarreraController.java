package com.example.tp_03_ejercicio_integrador.controllers;

import com.example.tp_03_ejercicio_integrador.dtos.EstudianteCarreraDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.modelos.Estudiante;
import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import com.example.tp_03_ejercicio_integrador.servicios.EstudianteCarreraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class EstudianteCarreraController {

    @Autowired
    private EstudianteCarreraServicio matriculacionServicio;

    // 2b) Matricular un estudiante en una carrera
    @PostMapping("/matricular")
    public ResponseEntity<?> matricularEstudiante(@RequestBody Estudiante estudiante, Carrera carrera) {
        try {
            EstudianteCarrera entity = new EstudianteCarrera(1, estudiante, carrera, Year.now().getValue(), 0, 0, false);
            EstudianteCarreraDTO estudianteCarreraDTO = matriculacionServicio.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteCarreraDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Obtener todas las inscripciones
    @GetMapping("")
    public ResponseEntity<?> obtenerTodasInscripciones() {
        try {
            List<EstudianteCarreraDTO> inscripciones = matriculacionServicio.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(inscripciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener inscripciones: " + e.getMessage() + "\"}");
        }
    }

    // Obtener una inscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerInscripcionPorId(@PathVariable int id) {
        try {
            EstudianteCarreraDTO inscripcion = matriculacionServicio.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(inscripcion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
