package com.example.microservicio_cuenta.service;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.repository.CuentaAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CuentaService {

    @Autowired
    CuentaAppRepository cuentaRepository;

    public List<CuentaApp> getAll() {
        return cuentaRepository.findAll();
    }

    public CuentaApp save(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void delete(CuentaApp cuenta) {
        cuentaRepository.delete(cuenta);
    }

    public CuentaApp findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    public CuentaApp update(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }
}