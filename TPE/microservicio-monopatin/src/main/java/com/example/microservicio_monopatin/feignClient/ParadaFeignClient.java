package com.example.microservicio_monopatin.feignClient;

import com.example.microservicio_monopatin.models.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "microservicio-parada", url="http://localhost:8085/paradas")
public interface ParadaFeignClient {

    @GetMapping("/{id}")
    Parada getParadaById(@PathVariable("id") Long paradaId);

    @PutMapping("/{idParada}/monopatin/{idMonopatin}/quitarMonopatin")
    Parada quitarMonopatin(@PathVariable("idParada") Long idParada, @PathVariable("idMonopatin") Long idMonopatin);
}
