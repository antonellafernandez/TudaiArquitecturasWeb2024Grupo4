package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_cuenta")
public interface CuentaFeignClient {

    @GetMapping("api/cuentas/")
    List<Cuenta> getAll();

    @PostMapping("api/cuentas")
    Cuenta save(@RequestBody Cuenta cuenta);

    @DeleteMapping("api/cuentas/{id}")
    void anularCuenta(@PathVariable("id") Long id);

    @PutMapping("api/cuentas/{id}")
    void restablecerCuenta(@PathVariable("id") Long id);
}
