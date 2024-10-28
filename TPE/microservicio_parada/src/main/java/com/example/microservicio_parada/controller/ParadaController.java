package com.example.microservicio_parada.controller;

import com.example.microservicio_parada.entity.Parada;
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

    @GetMapping("/")
    public ResponseEntity<List<Parada>> getAllParadas() {
        List<Parada> paradas = paradaService.getAll();
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

    @PostMapping("")
    public ResponseEntity<Parada> save(@RequestBody Parada parada) {
        Parada paradaNew = paradaService.save(parada);
        return ResponseEntity.ok(paradaNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        paradaService.delete(parada);
        return ResponseEntity.noContent().build();
    }
}