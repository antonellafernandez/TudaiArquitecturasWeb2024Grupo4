package com.example.tp_03_ejercicio_integrador.controllers;

import com.example.tp_03_ejercicio_integrador.dtos.CarreraConCantInscriptosDTO;
import com.example.tp_03_ejercicio_integrador.dtos.CarreraDTO;
import com.example.tp_03_ejercicio_integrador.dtos.ReporteCarreraDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.servicios.CarreraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraServicio carreraServicio;

    // Crear una nueva carrera
    @PostMapping("/alta")
    public ResponseEntity<?> crearCarrera(@RequestBody Carrera carrera) {
        try {
            Carrera carreraCreada = carreraServicio.save(carrera);
            CarreraDTO carreraDTO = carreraServicio.toDTO(carreraCreada);
            return ResponseEntity.status(HttpStatus.CREATED).body(carreraDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Obtener todas las carreras
    @GetMapping("")
    public ResponseEntity<?> obtenerTodasCarreras() {
        try {
            List<Carrera> carreras = carreraServicio.findAll();
            List<CarreraDTO> carreraDTOS = new ArrayList<>();
            for (Carrera c : carreras) {
                carreraDTOS.add(CarreraServicio.toDTO(c));
            }
            return ResponseEntity.status(HttpStatus.OK).body(carreraDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener carreras.\"}");
        }
    }

    // Obtener una carrera por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCarreraPorId(@PathVariable int id) {
        try {
            Carrera carreraCreada = carreraServicio.findById(id);
            CarreraDTO carreraDTO = carreraServicio.toDTO(carreraCreada);
            return ResponseEntity.status(HttpStatus.OK).body(carreraDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Actualizar una carrera
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCarrera(@PathVariable int id, @RequestBody Carrera carrera) {
        try {
            Carrera carreraActualizada = carreraServicio.update(id, carrera);
            return ResponseEntity.status(HttpStatus.OK).body(carreraActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Eliminar una carrera
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCarrera(@PathVariable int id) {
        try {
            boolean eliminado = carreraServicio.delete(id);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Carrera no encontrada.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // 2h) Generar un reporte de las carreras
    @GetMapping("/reporte")
    public ResponseEntity<?> generarReporteCarreras() { // VEEEEEEEEEEEEEER CONSULTA
        try {
            List<ReporteCarreraDTO> reporte = carreraServicio.generarReporteCarreras();
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // 2f) Recuperar carreras ordenadas por cantidad de inscriptos
    @GetMapping("/ordenadas-inscriptos")
    public ResponseEntity<?> getCarrerasOrdenadasPorInscriptos() {
        try {
            List<CarreraConCantInscriptosDTO> carreras = carreraServicio.getCarrerasOrdenadasPorInscriptos();
            return ResponseEntity.status(HttpStatus.OK).body(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
