package com.example.microservicio_monopatin.feignClient;

import com.example.microservicio_parada.entity.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-parada", url="http://localhost:8086/paradas")
public interface ParadaFeignClient {

    @GetMapping("/parada/{id}")
    Parada getParadaById(@PathVariable("id") Long paradaId);
}
