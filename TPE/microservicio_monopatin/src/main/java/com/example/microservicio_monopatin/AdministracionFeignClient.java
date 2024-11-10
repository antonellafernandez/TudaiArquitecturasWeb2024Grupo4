package com.example.microservicio_monopatin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservicio-mantenimiento", url = "")
public interface AdministracionFeignClient {

    @GetMapping("/mantenimiento/precio-base")
    double obtenerPrecioBase();

    @GetMapping("/mantenimiento/tarifa-extra")
    double obtenerTarifaExtra();

    // Aplicar tarifa extra para un monopatín específico después de una pausa prolongada
    @PostMapping("/mantenimiento/aplicar-tarifa-extra")
    void aplicarTarifaExtra(@RequestParam("monopatinId") Long monopatinId,
                            @RequestParam("tarifa") double tarifa);

}