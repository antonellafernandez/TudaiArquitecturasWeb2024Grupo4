package com.example.microservicio_administrador.controller;

import com.example.microservicio_administrador.feignClient.ParadaFeignClient;
import com.example.microservicio_administrador.model.Parada;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores/paradas")
public class ParadaController {
    @Autowired
    private ParadaFeignClient paradaFeignClient;

    @Operation(summary = "Permite al adm añadir parada", description = "Añade una nueva parada")
    @PostMapping("")
    public ResponseEntity<Parada> addParada(@RequestBody Parada newParada) {
        Parada parada = paradaFeignClient.save(newParada);
        if (parada == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(parada);
    }

    @Operation(summary = "Permite al adm habilitar parada", description = "Habilita una parada")
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<Parada> habilitarParada(@PathVariable Long id) {
        Parada p = paradaFeignClient.habilitarParada(id);
        if (p == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(p);
    }

    @Operation(summary = "Permite al adm deshabilitar parada", description = "Deshabilita una parada")
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<Parada> deshabilitarParada(@PathVariable Long id) {
        Parada p = paradaFeignClient.deshabilitarParada(id);
        if (p == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(p);
    }

    @Operation(summary = "Permite al adm actualizar parada", description = "Actualiza una parada por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Parada> updateParada(@PathVariable Long id, @RequestBody Parada newParada) {
        Parada p = paradaFeignClient.updateParada(id, newParada);
        if (p == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(p);
    }
}
