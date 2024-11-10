package com.example.microservicio_administrador.controller;

import com.example.microservicio_administrador.feignClient.MonopatinFeignClient;
import com.example.microservicio_administrador.model.Monopatin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrador/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinFeignClient paradaFeignClient;


    @PostMapping("/")
    public ResponseEntity<Monopatin> addMonopatin(@RequestBody Monopatin newMonopatin) {
        Monopatin monopatin = paradaFeignClient.save(newMonopatin);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<Monopatin> habilitarMonopatin(@PathVariable Long id){
        Monopatin monopatin = paradaFeignClient.habilitarMonopatin(id);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<Monopatin>deshabilitarMonopatin(@PathVariable Long id){
        Monopatin monopatin = paradaFeignClient.deshabilitarMonopatin(id);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monopatin>updateMonopatin(@PathVariable Long id, @RequestBody Monopatin newMonopatin) {
        Monopatin monopatin = paradaFeignClient.updateMonopatin(id, newMonopatin);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }
}
