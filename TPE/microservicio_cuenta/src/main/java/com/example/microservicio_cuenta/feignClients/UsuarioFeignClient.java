package com.example.microservicio_cuenta.feignClients;

import com.example.microservicio_cuenta.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="microservicio_usuario", url="http://localhost:8085/usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/{id}")
    Usuario getUsuarioById(@PathVariable("id") Long id);

    @PostMapping("")
    Usuario save(@RequestBody Usuario usuario);
}
