package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Monopatin;
import com.example.microservicio_administrador.model.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-monopatin")
public interface MonopatinFeignClient {

    @GetMapping("")
    List<Monopatin> getAll();

    @PostMapping("")
    Monopatin save(@RequestBody Monopatin monopatin);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id);

    @PostMapping("/habilitar/{id}")
    Monopatin deshabilitarMonopatin(@PathVariable("id") Long id);

    @PostMapping("/deshabilitar/{id}")
    Monopatin habilitarMonopatin(@PathVariable("id") Long id);

    @PutMapping("/deshabilitar/{id}")
    Monopatin updateMonopatin(@PathVariable("id") Long id, @RequestBody Monopatin Monopatin);
}
