package com.example.microservicio_administrador.controller;

import com.example.microservicio_administrador.feignClient.MonopatinFeignClient;
import com.example.microservicio_administrador.model.Monopatin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    /*
    3e. Como administrador quiero consultar la cantidad de monopatines actualmente en operación,
    versus la cantidad de monopatines actualmente en mantenimiento.
    */
    @GetMapping("/enOperacion")
    public ResponseEntity<String> consultarEnOperacion() {
        List<Monopatin> monopatines = monopatinFeignClient.getAll();

        if (monopatines == null || monopatines.isEmpty()) {
            return ResponseEntity.ok("No hay monopatines disponibles.");
        }

        long enOperacion = monopatines.stream()
                .filter(monopatin -> Boolean.TRUE.equals(monopatin.getDisponible()) ||
                        (Boolean.FALSE.equals(monopatin.getDisponible()) && monopatin.getViajeActivo() != null))
                .count();

        long enMantenimiento = monopatines.size() - enOperacion;

        String resultado = String.format("Monopatines en operación: %d, Monopatines en mantenimiento: %d", enOperacion, enMantenimiento);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("")
    public ResponseEntity<Monopatin> addMonopatin(@RequestBody Monopatin newMonopatin) {
        Monopatin monopatin = monopatinFeignClient.save(newMonopatin);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<Monopatin> habilitarMonopatin(@PathVariable Long id){
        Monopatin monopatin = monopatinFeignClient.habilitarMonopatin(id);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<Monopatin>deshabilitarMonopatin(@PathVariable Long id){
        Monopatin monopatin = monopatinFeignClient.deshabilitarMonopatin(id);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monopatin>updateMonopatin(@PathVariable Long id, @RequestBody Monopatin newMonopatin) {
        Monopatin monopatin = monopatinFeignClient.updateMonopatin(id, newMonopatin);
        if (monopatin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(monopatin);
    }
}
