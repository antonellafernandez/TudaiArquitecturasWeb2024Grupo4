package com.example.microservicio_administrador.controller;

import com.example.microservicio_administrador.dto.TarifaDto;
import com.example.microservicio_administrador.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores/tarifas")
public class TarifaController {

    @Autowired
    TarifaService tarifaService;

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> getTarifaByTipo(@PathVariable String tipo) {
        try {
            TarifaDto tarifaDto = tarifaService.getTarifaByTipo(tipo);
            return ResponseEntity.status(HttpStatus.OK).body(tarifaDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getTarifas() {
        try {
            List<TarifaDto> tarifas = tarifaService.getTarifas();
            return ResponseEntity.status(HttpStatus.OK).body(tarifas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener tarifas.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody TarifaDto tarifaDto) {
        try {
            TarifaDto tarifa = tarifaService.save(tarifaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarifa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TarifaDto tarifaDto) {
        try {
            TarifaDto tarifa = tarifaService.update(id, tarifaDto);
            return ResponseEntity.status(HttpStatus.OK).body(tarifa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (tarifaService.delete(id)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Tarifa no encontrada.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    @GetMapping("/normales")
    public ResponseEntity<?> getTarifasNormales() {
        try {
            List<TarifaDto> tarifas = tarifaService.getTarifas();
            return ResponseEntity.status(HttpStatus.OK).body(tarifas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener tarifas.\"}");
        }
    }
}
