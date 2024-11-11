package com.example.microservicio_monopatin.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;

@FeignClient(name = "microservicio_viaje", url = "http://localhost:8087/viajes")
public interface ViajeFeignClient {

    // Registrar el inicio de una pausa en el microservicio de Viaje
    @PostMapping("/viaje/registrarInicioPausa")
    void registrarInicioPausa(@RequestParam("id") Long idViaje,
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

    // Método para iniciar un viaje
    @PostMapping("/viaje/iniciar")
    void iniciarViaje(@RequestParam Long monopatinId, @RequestParam LocalDateTime fechaHoraInicio);

    @GetMapping("/totalPausas")
    ResponseEntity<?> getPausasMonopatines();
}
