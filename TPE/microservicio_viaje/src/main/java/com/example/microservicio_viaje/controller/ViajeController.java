package com.example.microservicio_viaje.controller;

import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    @GetMapping("/")
    public ResponseEntity<List<Viaje>> getAllViajes() {
        List<Viaje> viajes = viajeService.getAll();
        return viajes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViajeById(@PathVariable Long id) {
        Viaje viaje = viajeService.findById(id);
        return viaje != null ? ResponseEntity.ok(viaje) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Viaje> saveViaje(@RequestBody Viaje viaje) {
        Viaje newViaje = viajeService.save(viaje);
        return ResponseEntity.ok(newViaje);
    }

    @PutMapping("/{id}/pausar")
    public ResponseEntity<Void> pausarViaje(@PathVariable Long id, @RequestParam("pausa") LocalDateTime pausa) {
        viajeService.pausarViaje(id, pausa);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        viajeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

