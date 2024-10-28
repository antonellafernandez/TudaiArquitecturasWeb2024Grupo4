package com.example.microservicio_cuenta.service;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.repository.CuentaAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaAppService {

    @Autowired
    private CuentaAppRepository cuentaAppRepository;

    public List<CuentaApp> getAllCuentas() {
        return cuentaAppRepository.findAll();
    }

    public Optional<CuentaApp> getCuentaById(Long id) {
        return cuentaAppRepository.findById(id);
    }

    public CuentaApp saveCuenta(CuentaApp cuentaApp) {
        return cuentaAppRepository.save(cuentaApp);
    }

    public void deleteCuenta(Long id) {
        cuentaAppRepository.deleteById(id);
    }

    @Transactional
    public void pausarMonopatinPorCuenta(Long cuentaId) {
        CuentaApp cuentaApp = cuentaAppRepository.findById(cuentaId)
                .orElseThrow(() -> new EntityNotFoundException("CuentaApp no encontrada con ID: " + cuentaId));

        cuentaApp.getUsuarios().forEach(usuario -> {
            Monopatin monopatin = usuario.getMonopatin();
            if (monopatin != null && monopatin.isDisponible()) {
                monopatin.setDisponible(false);
            }
        });
        cuentaAppRepository.save(cuentaApp); // Se asegura de que el cambio sea persistente
    }



}
