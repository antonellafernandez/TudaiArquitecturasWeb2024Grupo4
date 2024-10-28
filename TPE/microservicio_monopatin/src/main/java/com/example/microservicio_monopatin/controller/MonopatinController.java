package com.example.microservicio_monopatin.controller;

import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinRepository monopatinRepository;

    @GetMapping("/")
    public List<Monopatin> getAllMonopatines() {
        return monopatinRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> getMonopatinById(@PathVariable Long id) {
        return monopatinRepository.findById(id)
                .map(monopatin -> ResponseEntity.ok(monopatin))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Monopatin createMonopatin(@RequestBody Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monopatin> updateMonopatin(@PathVariable Long id, @RequestBody Monopatin monopatin) {
        return monopatinRepository.findById(id)
                .map(existingMonopatin -> {
                    monopatin.setIdMonopatin(existingMonopatin.getIdMonopatin());
                    return ResponseEntity.ok(monopatinRepository.save(monopatin));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonopatin(@PathVariable Long id) {
        return monopatinRepository.findById(id)
                .map(monopatin -> {
                    monopatinRepository.delete(monopatin);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}/pausar")
    public ResponseEntity<Void> pausarMonopatin(@PathVariable Long id) {
        boolean resultado = monopatinService.pausarMonopatin(id);
        if (resultado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Ya está pausado
        }
    }

    @PutMapping("/{id}/reanudar")
    public ResponseEntity<Void> reanudarMonopatin(@PathVariable Long id) {
        boolean resultado = monopatinService.reanudarMonopatin(id);
        if (resultado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Ya está disponible
        }
    }
}
