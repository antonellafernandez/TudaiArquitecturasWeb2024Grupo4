package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.microservicio_administrador.model.Viaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-viaje")
public interface ViajeFeignClient {

    @GetMapping("")
    List<Viaje> getAll();

    @PostMapping("")
    Viaje save(@RequestBody Viaje viaje);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id);

    @GetMapping("/monopatines/anio/{anio}/cantViajes/{cantViajes}")
    List<Viaje> getMonopatinesByCantidadViajesYAnio(@PathVariable Long cantViajes, @PathVariable Long anio);

    @GetMapping("/viajes/facturado")
    ReporteTotalFacturadoEntreMesesDeAnio getReporteTotalFacturadoEntreMesesDeAnio(
            @RequestParam Long mesInicio,
            @RequestParam Long mesFin,
            @RequestParam Long anio);
}
