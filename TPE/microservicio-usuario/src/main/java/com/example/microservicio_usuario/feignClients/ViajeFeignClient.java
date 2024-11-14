package com.example.microservicio_usuario.feignClients;

import com.example.microservicio_usuario.models.Viaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(name = "microservicio-viaje", url="http://localhost:8087/viajes")
public interface ViajeFeignClient {

    @PostMapping("/iniciar")
    Viaje iniciarViaje(@RequestParam("monopatinId") Long monopatinId,
                       @RequestParam("fechaHoraInicio") LocalDateTime fechaHoraInicio);

    @PutMapping("/asociarCuenta")
    void asociarCuenta(@RequestParam("idViaje") Long idViaje,
                       @RequestParam("idCuenta") Long idCuenta);

    @GetMapping("/{idViaje}")
    Long getViaje(@PathVariable Long idViaje);
}