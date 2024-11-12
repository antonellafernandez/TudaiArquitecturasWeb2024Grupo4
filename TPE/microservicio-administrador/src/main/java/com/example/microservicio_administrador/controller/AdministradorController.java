package com.example.microservicio_administrador.controller;


import com.example.microservicio_administrador.dto.AdministradorDto;
import com.example.microservicio_administrador.feignClient.ParadaFeignClient;
import com.example.microservicio_administrador.feignClient.ViajeFeignClient;
import com.example.microservicio_administrador.model.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.microservicio_administrador.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ViajeFeignClient viajeFeignClient;

    @GetMapping("")
    public ResponseEntity<List<AdministradorDto>> getAllAdministradores() {
        List<AdministradorDto> administradores = administradorService.getAllAdministradores();
        if (administradores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorDto> getAdministradorById(@PathVariable Long id) {
        AdministradorDto admin = administradorService.getAdministradorById(id);
        if (admin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorDto> updateAdministrador(@PathVariable Long id, AdministradorDto admin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping
    public ResponseEntity<AdministradorDto> save(AdministradorDto newAdmin) {
        AdministradorDto admin = administradorService.save(newAdmin);

        if (admin == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getReporteTotalFacturadoEntreMesesDeAnio(@RequestParam Long mesInicio,
                                                                      @RequestParam Long mesFin,
                                                                      @RequestParam Long anio){
        try {
            ReporteTotalFacturadoEntreMesesDeAnio reporte = viajeFeignClient.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio);
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
