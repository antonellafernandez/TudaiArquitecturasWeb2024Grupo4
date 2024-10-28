package com.example.microservicio_parada.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/paradas")
public class ParadaController {

    @Autowired
    private ParadaService paradaService;

    @GetMapping("/")
    public List<Parada> getAllParadas() {
        return paradaService.getAllParadas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable Long id) {
        return paradaService.getParadaById(id)
                .map(parada -> ResponseEntity.ok(parada))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Parada createParada(@RequestBody Parada parada) {
        return paradaService.createParada(parada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parada> updateParada(@PathVariable Long id, @RequestBody Parada parada) {
        try {
            return ResponseEntity.ok(paradaService.updateParada(id, parada));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParada(@PathVariable Long id) {
        paradaService.deleteParada(id);
        return ResponseEntity.noContent().build();
    }
}
