package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_monopatin.entity.Monopatin;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UsuarioFeignClient {

    @GetMapping("api/monopatines/")
    List<Monopatin> getAll();

    @PostMapping("api/monopatines")
    Monopatin save(@RequestBody Monopatin monopatin);

    @DeleteMapping("api/monopatines/{id}")
    void delete(@PathVariable("id") Long id);
}
