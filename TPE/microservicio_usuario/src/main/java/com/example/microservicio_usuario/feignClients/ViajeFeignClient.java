package com.example.microservicio_usuario.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "viaje-service")
public interface ViajeFeignClient {

    @PostMapping("/iniciar")
    void iniciarViaje(@RequestParam("monopatinId") Long monopatinId,
                      @RequestParam("fechaHoraInicio") LocalDateTime fechaHoraInicio);

}