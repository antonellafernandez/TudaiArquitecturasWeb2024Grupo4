package com.example.microservicio_usuario.service;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}