package com.example.microservicio_cuenta.controller;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;
    // Create
    @PostMapping("")
    public ResponseEntity<CuentaApp> save(@RequestBody CuentaApp cuenta) {
        CuentaApp cuentaNew = cuentaService.save(cuenta);
        return ResponseEntity.ok(cuentaNew);
    }

    // Read
    @GetMapping("/")
    public ResponseEntity<List<CuentaApp>> getAllCuentas() {
        List<CuentaApp> cuentas = cuentaService.getAll();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/habilitadas")
    public ResponseEntity<List<CuentaApp>> getAllCuentasHabilitadas() {
        List<CuentaApp> cuentas = cuentaService.getAllHabilitadas();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/deshabilitadas")
    public ResponseEntity<List<CuentaApp>> getAllCuentasDeshabilitadas() {
        List<CuentaApp> cuentas = cuentaService.getAllDeshabilitadas();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaApp> getCuentaById(@PathVariable("id") Long id) {
        CuentaApp cuenta = cuentaService.findById(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuenta);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        CuentaApp cuenta = cuentaService.findById(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        cuentaService.delete(cuenta);
        return ResponseEntity.noContent().build();
    }
    // Habilitar
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<Void> habilitar(@PathVariable("id") Long id) {
        CuentaApp cuenta = cuentaService.findById(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        cuentaService.habilitar(id);
        return ResponseEntity.noContent().build();
    }
    // Deshabilitar
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<Void> deshabilitar(@PathVariable("id") Long id) {
        CuentaApp cuenta = cuentaService.findById(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        cuentaService.deshabilitar(id);
        return ResponseEntity.noContent().build();
    }
}
