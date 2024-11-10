package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio_cuenta", url="")
public interface CuentaFeignClient {

    @GetMapping("api/cuentas/")
    ResponseEntity<?> getAll();

    @PostMapping("api/cuentas")
    ResponseEntity<?> save(@RequestBody Cuenta cuenta);

    @DeleteMapping("api/cuentas/{id}")
    ResponseEntity<?> deshabilitarCuenta(@PathVariable("id") Long id);

    @PutMapping("api/cuentas/{id}")
    ResponseEntity<?> habilitarCuenta(@PathVariable("id") Long id);
}
