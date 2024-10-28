package com.example.microserviciocuentaapp.controller;

import com.example.microserviciocuentaapp.entity.CuentaApp;
import com.example.microserviciocuentaapp.service.CuentaAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaAppController {

    @Autowired
    private CuentaAppService cuentaAppService;

    @GetMapping
    public ResponseEntity<List<CuentaApp>> getAllCuentas() {
        return ResponseEntity.ok(cuentaAppService.getAllCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaApp> getCuentaById(@PathVariable Long id) {
        Optional<CuentaApp> cuentaApp = cuentaAppService.getCuentaById(id);
        return cuentaApp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CuentaApp> saveCuenta(@RequestBody CuentaApp cuentaApp) {
        return ResponseEntity.ok(cuentaAppService.saveCuenta(cuentaApp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaAppService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }

//Endopoint para pausar monopatin
    @PutMapping("/{id}/pausar-monopatin")
    public ResponseEntity<Void> pausarMonopatinPorCuenta(@PathVariable Long id) {
        cuentaAppService.pausarMonopatinPorCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
