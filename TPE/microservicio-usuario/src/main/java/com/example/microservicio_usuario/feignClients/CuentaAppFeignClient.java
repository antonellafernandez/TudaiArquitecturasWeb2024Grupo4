package com.example.microservicio_usuario.feignClients;

import com.example.microservicio_usuario.models.CuentaApp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservicio-cuenta", url = "http://localhost:3309/cuentas")
public interface CuentaAppFeignClient {
    @GetMapping("/{id}")
    CuentaApp getCuentaById(@PathVariable("id") Long id);

    @GetMapping("/creditos/{id}")
    CuentaApp getMontoCreditos(@PathVariable("id") Long id);

    @PostMapping("")
    CuentaApp save(@RequestBody CuentaApp cuenta);
}