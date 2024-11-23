package com.example.microservicio_mantenimiento.controller;

import com.example.microservicio_mantenimiento.dto.MantenimientoDto;
import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import com.example.microservicio_mantenimiento.service.MantenimientoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;

    @Operation(summary = "Registrar mantenimiento", description = "Registra un mantenimiento para un monopatín por su ID")
    /*
    @PostMapping("/registrar/{id}")
    public ResponseEntity<?> registrarMantenimiento(@PathVariable Long id, @RequestBody Long umbralKm, @RequestBody Long umbralTiempo) {

        try {
            Mantenimiento mantenimiento = mantenimientoService.registrarMonopatinEnMantenimiento(id, umbralKm, umbralTiempo);
            return ResponseEntity.status(HttpStatus.OK).body(mantenimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
*/
    @PostMapping("/registrar/{id}")
    public ResponseEntity<?> registrarMantenimiento(@PathVariable Long id, @RequestBody MantenimientoDto request) {
        try {
            Mantenimiento mantenimiento = mantenimientoService.registrarMonopatinEnMantenimiento(id, request.getUmbralKm(), request.getUmbralTiempo());
            return ResponseEntity.status(HttpStatus.OK).body(mantenimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Finalizar mantenimiento", description = "Finaliza un mantenimiento por su ID")

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizarMantenimiento(@PathVariable Long id) {
        try {
            Mantenimiento mantenimiento = mantenimientoService.finalizarMantenimiento(id);
            return ResponseEntity.status(HttpStatus.OK).body(mantenimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
   
}
