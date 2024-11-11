package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_parada", url="http://localhost:3312/paradas")
public interface ParadaFeignClient {

    @GetMapping("")
    List<Parada> getAll();

    @PostMapping("")
    Parada save(@RequestBody Parada parada);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id);

    @PostMapping("/habilitar/{id}")
    Parada deshabilitarParada(@PathVariable("id") Long id);

    @PostMapping("/deshabilitar/{id}")
    Parada habilitarParada(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    Parada updateParada(@PathVariable("id") Long id, @RequestBody Parada parada);
}
