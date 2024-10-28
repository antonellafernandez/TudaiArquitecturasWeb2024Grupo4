package com.example.microservicio_usuario.controller;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        Usuario usuarioNew = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}