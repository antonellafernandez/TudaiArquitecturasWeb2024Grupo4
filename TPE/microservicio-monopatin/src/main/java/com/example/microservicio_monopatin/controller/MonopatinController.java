package com.example.microservicio_monopatin.controller;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import com.example.microservicio_monopatin.dtos.ReporteUsoDto;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.service.MonopatinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monopatines")

public class MonopatinController {

    @Autowired
    MonopatinService monopatinService;

    @Operation(summary = "Obtener todos los monopatines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de monopatines"),
            @ApiResponse(responseCode = "204", description = "No hay monopatines disponibles")
    })
    @GetMapping("")
    public ResponseEntity<List<MonopatinDTO>> getAllMonopatines() {
        List<MonopatinDTO> monopatines = monopatinService.getAll();
        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatines);
    }

    @Operation(summary = "Obtener un monopatín por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín encontrado"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable("id") Long id) {
        Monopatin monopatin = monopatinService.findById(id);
        if (monopatin == null) {
            return ResponseEntity.notFound().build();
        }
        MonopatinDTO monopatinDTO = new MonopatinDTO(monopatin);
        return ResponseEntity.ok(monopatinDTO);
    }


    @Operation(summary = "Crear un nuevo monopatín")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("")
    public ResponseEntity<MonopatinDTO> save(@RequestBody MonopatinDTO monopatinDTO) {
        MonopatinDTO monopatinNew = monopatinService.save(monopatinDTO);
        return ResponseEntity.ok(monopatinNew);
    }

    @Operation(summary = "Eliminar un monopatín por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Monopatín eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Monopatin monopatin = monopatinService.findById(id);
        if (monopatin == null) {
            return ResponseEntity.notFound().build();
        }
        monopatinService.delete(monopatin);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Habilitar un monopatín por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín habilitado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitar(@PathVariable("id") Long idMonopatin) {
        if (monopatinService.habilitar(idMonopatin)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deshabilitar un monopatín por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín deshabilitado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitar(@PathVariable("id") Long idMonopatin) {
        if (monopatinService.deshabilitar(idMonopatin)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Obtener reporte de monopatines por kilómetros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/reportePorKilometros")
    public ResponseEntity<?> getReportePorKilometros() {
        List<ReporteUsoDto> reporte = monopatinService.getReporteUsoMonopatinesPorKilometro();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Obtener reporte de monopatines por tiempo total")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/reportePorTiempoTotal")
    public ResponseEntity<?> getReporteMonopatinesPorTiempoConPausas() {
        List<ReporteUsoDto> reporte = monopatinService.getReporteUsoMonopatinesCompleto();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Obtener reporte de monopatines por tiempo sin pausas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/reportePorTiempoSinPausa")
    public ResponseEntity<?> getReporteMonopatinesPorTiempoSinPausas() {
        List<ReporteUsoDto> reporte = monopatinService.getReporteUsoMonopatinesCompletoSinPausa();
        if (reporte != null) {
            return ResponseEntity.ok(reporte);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Reservar un monopatín")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín reservado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín o parada no encontrada")
    })
    @PutMapping("/reservarMonopatin/parada/{idParada}/monopatin/{idMonopatin}/reservar")
    public ResponseEntity<?> reservarMonopatin(@PathVariable("idParada") Long idParada, @PathVariable("idMonopatin") Long idMonopatin) {
        MonopatinDTO monopatin = monopatinService.iniciarViaje(idParada, idMonopatin);
        if (monopatin != null) {
            return ResponseEntity.ok(monopatin);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Pausar un monopatín")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín pausado exitosamente")
    })
    @PutMapping("/pausa/{id}")
    public ResponseEntity<?> pausa(@PathVariable("id") Long idMonopatin) {
        monopatinService.pausarMonopatin(idMonopatin);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Finalizar recorrido de un monopatín")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recorrido finalizado exitosamente")
    })
    @PutMapping("/{idMonopatin}/finalizarRecorrido")
    public ResponseEntity<?> finalizarRecorrido(@PathVariable("idMonopatin") Long idMonopatin,
                                                @RequestParam Long paradaId,
                                                @RequestParam Long viajeId,
                                                @RequestParam Long kmRecorridos) {
        monopatinService.pararMonopatin(idMonopatin, paradaId, viajeId, kmRecorridos);
        return ResponseEntity.ok().build();
    }
}
