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
        return ResponseEntity.ok(monopatines);
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
        MonopatinDTO monopatinNew = monopatinService.save(monopatinDTO);
        return ResponseEntity.ok(monopatinNew);
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
    public ResponseEntity<?> habilitar(@PathVariable("id") Long idMonopatin) {
        if (monopatinService.habilitar(idMonopatin)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitar(@PathVariable("id") Long idMonopatin) {
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
        List<ReporteUsoDto> reporte = monopatinService.getReporteUsoMonopatinesCompleto();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reportePorTiempoSinPausa")
    public ResponseEntity<?> getReporteMonopatinesPorTiempoSinPausas(){
        List<ReporteUsoDto> reporte = monopatinService.getReporteUsoMonopatinesCompletoSinPausa();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/reservarMonopatin/parada/{idParada}/monopatin/{idMonopatin}/reservar")
    public ResponseEntity<?> reservarMonopatin(@PathVariable("idParada") Long idParada, @PathVariable("idMonopatin") Long idMonopatin) {
        MonopatinDTO monopatin = monopatinService.iniciarViaje(idParada, idMonopatin);
        if (monopatin != null) {
            return ResponseEntity.ok(monopatin);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/pausa/{id}")
    public ResponseEntity<?> pausa(@PathVariable("id") Long idMonopatin) {
        monopatinService.pausarMonopatin(idMonopatin);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idMonopatin}/finalizarRecorrido")
    public ResponseEntity<?> finalizarRecorrido(@PathVariable("idMonopatin") Long idMonopatin,
                                                @RequestParam Long paradaId,
                                                @RequestParam Long viajeId,
                                                @RequestParam Long kmRecorridos) {
        monopatinService.pararMonopatin(idMonopatin, paradaId, viajeId, kmRecorridos);
        return ResponseEntity.ok().build();
    }
}
