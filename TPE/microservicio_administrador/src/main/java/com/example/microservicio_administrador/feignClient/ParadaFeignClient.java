package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_parada", url="http://localhost:8086/paradas")
public interface ParadaFeignClient {

    @GetMapping("api/paradas/")
    List<Parada> getAll();

    @PostMapping("api/paradas")
    Parada save(@RequestBody Parada parada);

    @DeleteMapping("api/paradas/{id}")
    void delete(@PathVariable("id") Long id);

    @PostMapping("api/paradas/habilitar/{id}")
    Parada deshabilitarParada(@PathVariable("id") Long id);

    @PostMapping("api/paradas/deshabilitar/{id}")
    Parada habilitarParada(@PathVariable("id") Long id);

    @PutMapping("api/paradas/deshabilitar/{id}")
    Parada updateParada(@PathVariable("id") Long id, @RequestBody Parada parada);

}
