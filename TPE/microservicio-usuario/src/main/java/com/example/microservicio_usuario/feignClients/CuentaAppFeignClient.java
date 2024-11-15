package com.example.microservicio_usuario.feignClients;

import com.example.microservicio_usuario.models.CuentaApp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-cuenta", url = "http://localhost:8082/cuentas")
public interface CuentaAppFeignClient {
    @GetMapping("/{id}")
    CuentaApp getCuentaById(@PathVariable("id") Long id);

    @PostMapping("")
    CuentaApp save(@RequestBody CuentaApp cuenta);

    @PutMapping("/cobrarViaje")
    ResponseEntity<?> cobrarViaje(@RequestParam Long idCuenta, @RequestParam Long idViaje);
}
