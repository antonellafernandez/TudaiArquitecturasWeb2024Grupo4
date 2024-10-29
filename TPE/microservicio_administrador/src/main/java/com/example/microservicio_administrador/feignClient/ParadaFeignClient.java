package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_monopatin.entity.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_parada")
public interface ParadaFeignClient {

    @GetMapping("api/paradas/")
    List<Monopatin> getAll();

    @PostMapping("api/paradas")
    Monopatin save(@RequestBody Monopatin monopatin);

    @DeleteMapping("api/paradas/{id}")
    void delete(@PathVariable("id") Long id);

}
