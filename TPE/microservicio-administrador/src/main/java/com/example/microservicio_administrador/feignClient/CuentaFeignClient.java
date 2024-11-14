package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-cuenta", url="http://localhost:8082/cuentas")
public interface CuentaFeignClient {

    @GetMapping("")
    ResponseEntity<?> getAll();

    @PostMapping("")
    ResponseEntity<?> save(@RequestBody Cuenta cuenta);

    @PutMapping("/deshabilitar/{id}")
    ResponseEntity<?> deshabilitarCuenta(@PathVariable("id") Long id);

    @PutMapping("/habilitar/{id}")
    ResponseEntity<?> habilitarCuenta(@PathVariable("id") Long id);
}
