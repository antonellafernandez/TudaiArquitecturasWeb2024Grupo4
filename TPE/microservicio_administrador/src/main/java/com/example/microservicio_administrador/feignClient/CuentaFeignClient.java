package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_monopatin.entity.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio_cuenta")
public interface CuentaFeignClient {

    @GetMapping("api/cuentas/")
    List<Monopatin> getAll();

    @PostMapping("api/cuentas")
    Monopatin save(@RequestBody Monopatin monopatin);

    @DeleteMapping("api/cuentas/{id}")
    void delete(@PathVariable("id") Long id);
}
