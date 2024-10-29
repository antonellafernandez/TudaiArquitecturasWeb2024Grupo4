package com.example.microservicio_administrador.feignClient;

import com.example.microservicio_administrador.model.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UsuarioFeignClient {

    @GetMapping("api/monopatines/")
    List<Usuario> getAll();

    @PostMapping("api/monopatines")
    Usuario save(@RequestBody Usuario usuario);

    @DeleteMapping("api/monopatines/{id}")
    void delete(@PathVariable("id") Long id);
}
