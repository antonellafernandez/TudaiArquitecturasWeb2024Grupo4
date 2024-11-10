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
    // Create
    @PostMapping("")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        Usuario usuarioNew = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioNew);
    }
    // Read
    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/habilitados")
    public ResponseEntity<List<Usuario>> getAllUsuariosHabilitados() {
        List<Usuario> usuarios = usuarioService.getAllHabilitados();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/deshabilitados")
    public ResponseEntity<List<Usuario>> getAllUsuariosDeshabilitados() {
        List<Usuario> usuarios = usuarioService.getAllDeshabilitados();
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
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.delete(usuario);
        return ResponseEntity.noContent().build();
    }
    // Habilitar
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<Void> habilitar(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.habilitar(id);
        return ResponseEntity.noContent().build();
    }
    // Deshabilitar
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<Void> deshabilitar(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deshabilitar(id);
        return ResponseEntity.noContent().build();
    }
}
