package com.example.microservicio_monopatin.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservicio-administrador", url = "http://localhost:8081/administradores")
public interface AdministradorFeignClient {

    @GetMapping("/precio-base")
    double obtenerPrecioBase();

    @GetMapping("/tarifa-extra")
    double obtenerTarifaExtra();

    // Aplicar tarifa extra para un monopatín específico después de una pausa prolongada
    @PostMapping("/aplicar-tarifa-extra")
    void aplicarTarifaExtra(@RequestParam("monopatinId") Long monopatinId,
                            @RequestParam("tarifa") double tarifa);
}
