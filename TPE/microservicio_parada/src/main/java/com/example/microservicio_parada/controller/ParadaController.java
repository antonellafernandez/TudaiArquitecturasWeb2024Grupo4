package com.example.microservicio_parada.controller;

import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.models.Monopatin;
import com.example.microservicio_parada.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    @Autowired
    ParadaService paradaService;
    // Create
    @PostMapping("")
    public ResponseEntity<Parada> save(@RequestBody Parada parada) {
        Parada paradaNew = paradaService.save(parada);
        return ResponseEntity.ok(paradaNew);
    }
    // Read
    @GetMapping("")
    public ResponseEntity<List<Parada>> getAllParadas() {
        List<Parada> paradas = paradaService.getAll();
        if (paradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/habilitadas")
    public ResponseEntity<List<Parada>> getAllParadasHabilitadas() {
        List<Parada> paradas = paradaService.getAllHabilitadas();
        if (paradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/deshabilitadas")
    public ResponseEntity<List<Parada>> getAllParadasDeshabilitadas() {
        List<Parada> paradas = paradaService.getAllDeshabilitadas();
        if (paradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parada);
    }
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        paradaService.delete(parada);
        return ResponseEntity.noContent().build();
    }
    // Habilitar
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<Void> habilitar(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        paradaService.habilitar(id);
        return ResponseEntity.noContent().build();
    }
    // Deshabilitar
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<Void> deshabilitar(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        paradaService.deshabilitar(id);
        return ResponseEntity.noContent().build();
    }

    /*
    3g. Como usuario quiero un listado de los monopatines cercanos a mi zona, para poder encontrar
    un monopatín cerca de mi ubicación.
    */
    @GetMapping("/cercanos")
    public ResponseEntity<List<Monopatin>> getMonopatinesCercanos(
            @RequestParam double latitud,
            @RequestParam double longitud,
            @RequestParam double radio) {
        List<Monopatin> monopatinesCercanos = paradaService.getMonopatinesCercanos(latitud, longitud, radio);
        if (monopatinesCercanos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatinesCercanos);
    }
}
