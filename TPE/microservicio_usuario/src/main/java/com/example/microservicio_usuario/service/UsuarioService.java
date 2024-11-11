package com.example.microservicio_usuario.service;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.feignClients.CuentaAppFeignClient;
import com.example.microservicio_usuario.feignClients.MonopatinFeignClient;
import com.example.microservicio_usuario.models.CuentaApp;
import com.example.microservicio_usuario.models.Monopatin;
import com.example.microservicio_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CuentaAppFeignClient cuentaAppFeignClient;

    @Autowired
    MonopatinFeignClient monopatinFeignClient;

    // Create
    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Read
    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllHabilitados() {
        return usuarioRepository.findAllHabilitados();
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllDeshabilitados() {
        return usuarioRepository.findAllDeshabilitados();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Update
    @Transactional
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Delete
    @Transactional
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    // Habilitar Parada
    @Transactional
    public void habilitar(Long id) {
        usuarioRepository.habilitar(id);
    }

    // Habilitar Parada
    @Transactional
    public void deshabilitar(Long id) {
        usuarioRepository.deshabilitar(id);
    }

    // Read CuentaApps
    public List<CuentaApp> getCuentaAppsById(Long id) {
        List<CuentaApp> salida = new ArrayList<CuentaApp>();
        List<Long> idCuentaApps = usuarioRepository.getIdCuentaApps(id);

        for (Long idCuenta : idCuentaApps) {
            salida.add(cuentaAppFeignClient.getCuentaById(idCuenta));
        }

        return salida;
    }

    // Read Monopatin
    @Transactional(readOnly = true)
    public Monopatin getMonopatinById(Long id) {
        return monopatinFeignClient.getMonopatinById(id);
    }
}
