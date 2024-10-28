package com.example.microserviciousuario.controller;

import com.example.microserviciousuario.entity.Usuario;
import com.example.microserviciousuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return usuario == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
