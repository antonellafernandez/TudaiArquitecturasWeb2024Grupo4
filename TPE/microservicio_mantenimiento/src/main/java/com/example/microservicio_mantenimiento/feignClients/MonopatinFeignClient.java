package com.example.microservicio_mantenimiento.feignClients;

import com.example.microservicio_mantenimiento.models.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="microservicio_monopatin", url="http://localhost:8084/monopatines")
public interface MonopatinFeignClient {
    @GetMapping("/{id}")
    Monopatin getMonopatinById(@PathVariable("id") Long id);

    @PostMapping ("")
    Monopatin save(@RequestBody Monopatin monopatin);

    @PutMapping("/habilitar/{id}")
    ResponseEntity<?> habilitar(@PathVariable("id") Long id);

    @PutMapping("/deshabilitar/{id}")
    ResponseEntity<?> deshabilitar(@PathVariable("id") Long id);

}
