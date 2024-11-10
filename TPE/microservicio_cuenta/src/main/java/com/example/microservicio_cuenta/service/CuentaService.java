package com.example.microservicio_cuenta.service;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.feignClients.UsuarioFeignClient;
import com.example.microservicio_cuenta.models.Usuario;
import com.example.microservicio_cuenta.repository.CuentaAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CuentaService {

    @Autowired
    CuentaAppRepository cuentaRepository;

    @Autowired
    UsuarioFeignClient usuarioFeignClient;

    // Create
    public CuentaApp save(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Read
    public List<CuentaApp> getAll() {
        return cuentaRepository.findAll();
    }

    public List<CuentaApp> getAllHabilitadas() {
        return cuentaRepository.findAllHabilitadas();
    }

    public List<CuentaApp> getAllDeshabilitadas() {
        return cuentaRepository.findAllDeshabilitadas();
    }

    public CuentaApp findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    // Update
    public CuentaApp update(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Delete
    public void delete(CuentaApp cuenta) {
        cuentaRepository.delete(cuenta);
    }

    // Habilitar Cuenta
    @Transactional
    public void habilitar(Long id) {
        cuentaRepository.habilitar(id);
    }

    // Habilitar Cuenta
    @Transactional
    public void deshabilitar(Long id) {
        cuentaRepository.deshabilitar(id);
    }

    // Read Usuarios
    public List<Usuario> getUsuariosById(Long id) {
        List<Usuario> salida = new ArrayList<Usuario>();
        List<Long> idUsuarios = cuentaRepository.getIdUsuarios(id);

        for (Long idUsuario : idUsuarios) {
            salida.add(usuarioFeignClient.getUsuarioById(idUsuario));
        }

        return salida;
    }
}