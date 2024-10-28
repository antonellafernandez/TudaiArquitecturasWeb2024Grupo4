package com.example.microserviciocuentaapp.service;

import com.example.microserviciocuentaapp.entity.CuentaApp;
import com.example.microserviciocuentaapp.repository.CuentaAppRepository;
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

    public void pausarMonopatinPorCuenta(Long cuentaId) {
        CuentaApp cuentaApp = cuentaAppRepository.findById(cuentaId).orElse(null);
        if (cuentaApp != null) {
            cuentaApp.getUsuarios().forEach(usuario -> {
                if (usuario.getMonopatin() != null) {
                    usuario.getMonopatin().setDisponible(false);
                }
            });
            cuentaAppRepository.save(cuentaApp);
        }


}
