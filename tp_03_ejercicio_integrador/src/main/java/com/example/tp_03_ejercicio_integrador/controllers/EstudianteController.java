package com.example.tp_03_ejercicio_integrador.controllers;

import com.example.tp_03_ejercicio_integrador.dtos.EstudianteDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Estudiante;
import com.example.tp_03_ejercicio_integrador.servicios.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteServicio estudianteServicio;

    // 2a) Dar de alta un estudiante
    @PostMapping("/alta")
    public ResponseEntity<?> altaEstudiante(@RequestBody Estudiante estudiante) {
        try {
            Estudiante creado = estudianteServicio.save(estudiante);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // 2c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple. -> Por APELLIDO
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Estudiante> estudiantes = estudianteServicio.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener estudiantes.\"}");
        }
    }

    // 2d) Eecuperar un estudiante, en base a su número de libreta universitaria.
    @GetMapping("/lu/{lu}")
    public ResponseEntity<?> obtenerPorLu(@PathVariable Long lu) {
        try {
            Estudiante estudiante = estudianteServicio.obtenerPorLu(lu);
            EstudianteDTO estudianteDTO = estudianteServicio.toDTO(estudiante);
            return ResponseEntity.status(HttpStatus.OK).body(estudianteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    // 2e) Recuperar todos los estudiantes, en base a su género.
    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> obtenerPorGenero(@PathVariable String genero) {
        try {
            List<Estudiante> estudiantes = estudianteServicio.obtenerPorGenero(genero);
            List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
            for (Estudiante estudiante : estudiantes) {
                estudianteDTOS.add(EstudianteServicio.toDTO(estudiante));
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudianteDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // 2g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    @GetMapping("/carrera/{nombreCarrera}/ciudad/{ciudadResidencia}")
    public ResponseEntity<?> obtenerPorCarreraYCiudad(@PathVariable String nombreCarrera,
                                                      @PathVariable String ciudadResidencia) {
        try {
            List<Estudiante> estudiantes = estudianteServicio.getEstudiantesByCarreraAndCiudad(nombreCarrera, ciudadResidencia);
            List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
            for (Estudiante estudiante : estudiantes) {
                estudianteDTOS.add(EstudianteServicio.toDTO(estudiante));
            }
            return ResponseEntity.status(HttpStatus.OK).body(estudianteDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        try {
            Estudiante estudiante = estudianteServicio.findById(id);
            EstudianteDTO estudianteDTO = estudianteServicio.toDTO(estudiante);
            return ResponseEntity.status(HttpStatus.OK).body(estudianteDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Actualizar un estudiante
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEstudiante(@PathVariable int id, @RequestBody Estudiante estudiante) {
        try {
            Estudiante actualizado = estudianteServicio.update(id, estudiante);
            return ResponseEntity.status(HttpStatus.OK).body(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Eliminar un estudiante
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEstudiante(@PathVariable int id) {
        try {
            boolean eliminado = estudianteServicio.delete(id);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Estudiante no encontrado.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
