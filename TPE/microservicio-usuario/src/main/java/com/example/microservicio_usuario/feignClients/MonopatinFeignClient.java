package com.example.microservicio_usuario.feignClients;

import com.example.microservicio_usuario.models.Monopatin;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="microservicio-monopatin")
public interface MonopatinFeignClient {
    @GetMapping("/{id}")
    Monopatin getMonopatinById(@PathVariable("id") Long id);

    @PostMapping ("")
    Monopatin save(@RequestBody Monopatin monopatin);

    @PutMapping("/reservarMonopatin/parada/{idParada}/monopatin/{idMonopatin}/reservar")
    public ResponseEntity<?> reservarMonopatin(@PathVariable("idParada") Long idParada, @PathVariable("idMonopatin") Long idMonopatin);

    @PutMapping("/{idMonopatin}/finalizarRecorrido")
    public ResponseEntity<?> finalizarRecorrido(@PathVariable("idMonopatin") Long idMonopatin);
}
