package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_monopatin", url="")
public interface MonopatinFeignClient {

    @GetMapping("api/monopatines/")
    List<Monopatin> getAll();

    @PostMapping("api/monopatines")
    Monopatin save(@RequestBody Monopatin monopatin);

    @DeleteMapping("api/monopatines/{id}")
    void delete(@PathVariable("id") Long id);
}
