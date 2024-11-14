package com.example.microservicio_usuario.feignClients;

import com.example.microservicio_usuario.models.CuentaApp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-cuenta", url = "http://localhost:8082/cuentas")
public interface CuentaAppFeignClient {
    @GetMapping("/{id}")
    CuentaApp getCuentaById(@PathVariable("id") Long id);

    @GetMapping("/creditos/{id}")
    CuentaApp getMontoCreditos(@PathVariable("id") Long id);

    @PostMapping("")
    CuentaApp save(@RequestBody CuentaApp cuenta);

    @PutMapping("/cobrar")
    ResponseEntity<?> cobrarViaje(@RequestBody Long idCuenta, Long idViaje);
}
