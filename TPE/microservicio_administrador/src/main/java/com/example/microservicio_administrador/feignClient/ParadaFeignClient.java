package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_parada", url="")
public interface ParadaFeignClient {

    @GetMapping("api/paradas/")
    List<Parada> getAll();

    @PostMapping("api/paradas")
    Parada save(@RequestBody Parada parada);

    @DeleteMapping("api/paradas/{id}")
    void delete(@PathVariable("id") Long id);

}
