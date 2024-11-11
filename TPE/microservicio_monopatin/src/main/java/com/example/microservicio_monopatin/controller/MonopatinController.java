package com.example.microservicio_monopatin.controller;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import com.example.microservicio_monopatin.dtos.ReporteUsoDto;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.service.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monopatines")

public class MonopatinController {

    @Autowired
    MonopatinService monopatinService;

    @GetMapping("")
    public ResponseEntity<List<MonopatinDTO>> getAllMonopatines() {
        List<MonopatinDTO> monopatines = monopatinService.getAll();
        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convertir la lista de Monopatines a MonopatinDTO
        List<MonopatinDTO> monopatinDTOs = monopatines.stream()
                .map(MonopatinDTO::new) // Usar el constructor que convierte la entidad en DTO
                .collect(Collectors.toList());

        return ResponseEntity.ok(monopatinDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable("id") Long id) {
        Monopatin monopatin = monopatinService.findById(id);
        if (monopatin == null) {
            return ResponseEntity.notFound().build();
        }
        MonopatinDTO monopatinDTO = new MonopatinDTO(monopatin);
        return ResponseEntity.ok(monopatinDTO);
    }

    @PostMapping("")
    public ResponseEntity<MonopatinDTO> save(@RequestBody MonopatinDTO monopatinDTO) {
        Monopatin monopatin = new Monopatin(monopatinDTO);
        Monopatin monopatinNew = monopatinService.save(monopatin);
        MonopatinDTO monopatinNewDTO = new MonopatinDTO(monopatinNew);
        return ResponseEntity.ok(monopatinNewDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Monopatin monopatin = monopatinService.findById(id);
        if (monopatin == null) {
            return ResponseEntity.notFound().build();
        }
        monopatinService.delete(monopatin);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitar(@PathVariable Long idMonopatin) {
        if (monopatinService.habilitar(idMonopatin)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitar(@PathVariable Long idMonopatin) {
        if (monopatinService.deshabilitar(idMonopatin)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reportePorKilometros")
    public ResponseEntity<?> getReportePorKilometros(){
        List<ReporteUsoDto> reporte = monopatinService.getReporteUsoMonopatinesPorKilometro();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reportePorTiempoTotal")
    public ResponseEntity<?> getReporteMonopatinesPorTiempoConPausas(){
        List<ReporteUsoDto> reporte = monopatinService.getReporteMonopatinesPorTiempoConPausas();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reportePorTiempoSinPausa")
    public ResponseEntity<?> getReporteMonopatinesPorTiempoSinPausas(){
        List<ReporteUsoDto> reporte = monopatinService.getReporteMonopatinesPorTiempoSinPausas();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }
}
