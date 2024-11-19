package com.example.microservicio_monopatin.feignClient;

import com.example.microservicio_monopatin.models.Parada;
import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-parada")
public interface ParadaFeignClient {

    @GetMapping("/{id}")
    Parada getParadaById(@PathVariable("id") Long paradaId);

    @PutMapping("/{idParada}/monopatin/{idMonopatin}/quitarMonopatin")
    Parada quitarMonopatin(@PathVariable("idParada") Long idParada, @PathVariable("idMonopatin") Long idMonopatin);

    @PostMapping("")
    Parada save(Parada parada);
}
