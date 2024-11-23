package com.example.microservicio_cuenta.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(name="microservicio-viaje")

public interface ViajeFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<?> getViajeById(@PathVariable("id") Long id);

}
