package com.example.microservicio_mantenimiento.controller;

import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import com.example.microservicio_mantenimiento.service.MantenimientoService;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mantenimientos")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;

    @PutMapping("/registrar/{id}")
    public ResponseEntity<?> registrarMantenimiento(@PathVariable Long id, @RequestBody Long umbralKm, @RequestBody Long umbralTiempo) {

        try {
            Mantenimiento mantenimiento = mantenimientoService.registrarMonopatinEnMantenimiento(id, umbralKm, umbralTiempo);
            return ResponseEntity.status(HttpStatus.OK).body(mantenimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizarMantenimiento(@PathVariable Long idMantenimiento) {

        try {
            Mantenimiento mantenimiento = mantenimientoService.finalizarMantenimiento(idMantenimiento);
            return ResponseEntity.status(HttpStatus.OK).body(mantenimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
