package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Viaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_viaje")
public interface ViajeFeignClient {

    @GetMapping("api/viajes/")
    List<Viaje> getAll();

    @PostMapping("api/viajes")
    Viaje save(@RequestBody Viaje viaje);

    @DeleteMapping("api/viajes/{id}")
    void delete(@PathVariable("id") Long id);

    @GetMapping("api/viajes/monopatin/{id}")
    List<Viaje> getByMonopatinCantidadViajesYAnio(@PathVariable("id") Long id, Long cantViajes, int anio);
}
