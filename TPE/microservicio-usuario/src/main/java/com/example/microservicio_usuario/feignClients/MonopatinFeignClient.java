package com.example.microservicio_usuario.feignClients;

import com.example.microservicio_usuario.models.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="microservicio-monopatin", url="http://localhost:3310/monopatines")
public interface MonopatinFeignClient {
    @GetMapping("/{id}")
    Monopatin getMonopatinById(@PathVariable("id") Long id);

    @PostMapping ("")
    Monopatin save(@RequestBody Monopatin monopatin);
}
