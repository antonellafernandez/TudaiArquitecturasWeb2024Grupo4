package com.example.microservicio_cuenta.service;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.feignClients.UsuarioFeignClient;
import com.example.microservicio_cuenta.models.Usuario;
import com.example.microservicio_cuenta.repository.CuentaAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Service
public class CuentaService {

    @Autowired
    CuentaAppRepository cuentaRepository;

    @Autowired
    UsuarioFeignClient usuarioFeignClient;

    // Create
    @Transactional
    public CuentaApp save(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Read
    @Transactional(readOnly = true)
    public List<CuentaApp> getAll() {
        return cuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CuentaApp> getAllHabilitadas() {
        return cuentaRepository.findAllHabilitadas();
    }

    @Transactional(readOnly = true)
    public List<CuentaApp> getAllDeshabilitadas() {
        return cuentaRepository.findAllDeshabilitadas();
    }

    @Transactional(readOnly = true)
    public CuentaApp findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    // Update
    @Transactional
    public CuentaApp update(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Delete
    @Transactional
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
    @Transactional(readOnly = true)
    public List<Usuario> getUsuariosById(Long id) {
        List<Usuario> salida = new ArrayList<Usuario>();
        List<Long> idUsuarios = cuentaRepository.getIdUsuarios(id);

        for (Long idUsuario : idUsuarios) {
            salida.add(usuarioFeignClient.getUsuarioById(idUsuario));
        }

        return salida;
    }

    public void cobrarVaije(Long idCuenta, Long idViaje){
        CuentaApp cuenta = cuentaRepository.findById(idCuenta).orElse(null);

    }

    private Double getPrecioTotal(Long idViaje){
        return 0.0;
    }
}