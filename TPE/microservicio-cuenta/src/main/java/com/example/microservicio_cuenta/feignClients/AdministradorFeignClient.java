package com.example.microservicio_cuenta.feignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservicio-administrador")
public interface AdministradorFeignClient {

    @GetMapping("/tarifas/tipo/{tipo}")
    public ResponseEntity<?> getTarifaByTipo(@PathVariable String tipo);
}
