package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-usuario")
public interface UsuarioFeignClient {

    @GetMapping("")
    List<Usuario> getAll();

    @PostMapping("")
    Usuario save(@RequestBody Usuario usuario);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id);
}
