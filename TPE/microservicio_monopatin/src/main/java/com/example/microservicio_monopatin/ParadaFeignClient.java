package com.example.microservicio_monopatin;

import com.example.microservicio_parada.entity.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-parada", url = "http://microservicio-parada:8082")
public interface ParadaFeignClient {

    @GetMapping("/parada/{id}")
    Parada getParadaById(@PathVariable("id") Long paradaId);
}