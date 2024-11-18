package com.example.microservicio_cuenta.controller;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;
    
    @Operation(summary = "Crear una nueva cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    // Create
    @PostMapping("")
    public ResponseEntity<CuentaApp> save(@RequestBody CuentaApp cuenta) {
        CuentaApp cuentaNew = cuentaService.save(cuenta);
        return ResponseEntity.ok(cuentaNew);
    }

    @Operation(summary = "Obtener todas las cuentas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas"),
            @ApiResponse(responseCode = "204", description = "No hay cuentas disponibles")
    })
    // Read
    @GetMapping("")
    public ResponseEntity<List<CuentaApp>> getAllCuentas() {
        List<CuentaApp> cuentas = cuentaService.getAll();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @Operation(summary = "Obtener todas las cuentas habilitadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas habilitadas"),
            @ApiResponse(responseCode = "204", description = "No hay cuentas habilitadas disponibles")
    })
    @GetMapping("/habilitadas")
    public ResponseEntity<List<CuentaApp>> getAllCuentasHabilitadas() {
        List<CuentaApp> cuentas = cuentaService.getAllHabilitadas();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @Operation(summary = "Obtener todas las cuentas deshabilitadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas deshabilitadas"),
            @ApiResponse(responseCode = "204", description = "No hay cuentas deshabilitadas disponibles")
    })
    @GetMapping("/deshabilitadas")
    public ResponseEntity<List<CuentaApp>> getAllCuentasDeshabilitadas() {
        List<CuentaApp> cuentas = cuentaService.getAllDeshabilitadas();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }


    @Operation(summary = "Obtener una cuenta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CuentaApp> getCuentaById(@PathVariable("id") Long id) {
        CuentaApp cuenta = cuentaService.findById(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "Eliminar una cuenta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
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

    @Operation(summary = "Habilitar una cuenta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta habilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
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

    @Operation(summary = "Deshabilitar una cuenta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta deshabilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
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

    @Operation(summary = "Cobrar un viaje de una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viaje cobrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/cobrarViaje")
    public ResponseEntity<Void> cobrarViaje(@RequestParam Long idCuenta, @RequestParam Long idViaje) {
        cuentaService.cobrarViaje(idCuenta, idViaje);
        return ResponseEntity.ok().build();
    }
}
