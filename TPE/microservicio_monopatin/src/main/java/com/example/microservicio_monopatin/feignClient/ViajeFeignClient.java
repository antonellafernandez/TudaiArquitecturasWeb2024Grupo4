package com.example.microservicio_monopatin.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "viaje-service")
public interface ViajeFeignClient {

    // Registrar el inicio de una pausa en el microservicio de Viaje
    @PostMapping("/viaje/registrarInicioPausa")
    void registrarInicioPausa(@RequestParam("monopatinId") Long monopatinId,
                              @RequestParam("fechaHoraInicio") LocalDateTime fechaHoraInicio);

    // Obtener el inicio de la última pausa del viaje
    @GetMapping("/viaje/obtenerInicioUltimaPausa")
    LocalDateTime obtenerInicioUltimaPausa(@RequestParam("monopatinId") Long monopatinId);

    // Registrar el fin de una pausa en el microservicio de Viaje
    @PostMapping("/viaje/registrarFinPausa")
    void registrarFinPausa(@RequestParam("monopatinId") Long monopatinId,
                           @RequestParam("fechaHoraFin") LocalDateTime fechaHoraFin);

    @PutMapping("/viaje/finalizar")
    void finalizarViaje(@RequestParam("viajeId") Long viajeId,
                        @RequestParam("fechaHoraFin") LocalDateTime fechaHoraFin,
                        @RequestParam("kmRecorridos") Long kmRecorridos);
}
