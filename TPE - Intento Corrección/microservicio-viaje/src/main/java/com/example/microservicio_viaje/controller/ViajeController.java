package com.example.microservicio_viaje.controller;

import com.example.microservicio_viaje.dto.ReporteMonopatinesPorViajesYAnio;
import com.example.microservicio_viaje.dto.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.service.ViajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    @Autowired
    ViajeService viajeService;

    @Operation(summary = "Crear u nuevo viaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viaje creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    // Create
    @PostMapping("")
    public ResponseEntity<Viaje> save(@RequestBody Viaje viaje) {
        Viaje viajeNew = viajeService.save(viaje);
        return ResponseEntity.ok(viajeNew);
    }

    @Operation(summary = "Obtener todos los viajes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de viajes obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron viajes")
    })
    // Read
    @GetMapping("")
    public ResponseEntity<List<Viaje>> getAllViajes() {
        List<Viaje> viajes = viajeService.getAll();
        if (viajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viajes);
    }

    @Operation(summary = "Obtener un viaje por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viaje obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViajeById(@PathVariable("id") Long id) {
        Viaje viaje = viajeService.findById(id);
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @Operation(summary = "Eliminar un viaje por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Viaje eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado", content = @Content)
    })
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Viaje viaje = viajeService.findById(id);
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        viajeService.delete(viaje);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Asociar una cuenta a un viaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta asociada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Viaje o cuenta no encontrado", content = @Content)
    })
    // Asociar cuenta
    @PutMapping("/asociarCuenta")
    public ResponseEntity<Void> asociarCuenta(@RequestParam Long idViaje,
                                              @RequestParam Long idCuenta) {
        viajeService.asociarCuenta(idViaje, idCuenta);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Registrar inicio de pausa en un viaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pausa registrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado", content = @Content)
    })
    // Registrar inicio de pausa
    @PostMapping("/registrarPausa")
    public ResponseEntity<Void> registrarPausa(@RequestParam("id") Long idViaje,
                                               @RequestParam("fechaHora") LocalDateTime fechaHora) {
        viajeService.registrarPausa(idViaje, fechaHora);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener el inicio de la última pausa de un monopatín")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de pausa obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content)
    })
    // Obtener el inicio de la última pausa
    @GetMapping("/obtenerInicioUltimaPausa")
    public ResponseEntity<LocalDateTime> obtenerInicioUltimaPausa(@RequestParam("monopatinId") Long monopatinId) {
        LocalDateTime inicioUltimaPausa = viajeService.obtenerInicioUltimaPausa(monopatinId);
        return ResponseEntity.ok(inicioUltimaPausa);
    }

    @Operation(summary = "Finalizar un viaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viaje finalizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado", content = @Content)
    })
    // Finalizar viaje
    @PutMapping("/finalizar")
    public ResponseEntity<Void> finalizarViaje(@RequestParam("viajeId") Long viajeId,
                                               @RequestParam("fechaHoraFin") LocalDateTime fechaHoraFin,
                                               @RequestParam("kmRecorridos") Long kmRecorridos) {
        viajeService.finalizarViaje(viajeId, fechaHoraFin, kmRecorridos);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Iniciar un viaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viaje iniciado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    // Iniciar viaje
    @PostMapping("/iniciar")
    public Viaje iniciarViaje(@RequestParam Long monopatinId,
                                             @RequestParam LocalDateTime fechaHoraInicio) {
        return viajeService.iniciarViaje(monopatinId, fechaHoraInicio);
    }

    @Operation(summary = "Obtener tiempo total de pausas por monopatín")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración de pausas obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Datos no encontrados", content = @Content)
    })
    // Obtener pausas monopatines
    @GetMapping("/totalPausas")
    public ResponseEntity<?> getPausasMonopatines() {
        try {
            Map<Long, Long> tiempoPausas = viajeService.getDuracionPausas();
            return ResponseEntity.status(HttpStatus.OK).body(tiempoPausas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Obtener monopatines por cantidad de viajes y año")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Datos no encontrados", content = @Content)
    })
    // Obtener monopatines por cantidad de viajes y año
    @GetMapping("/monopatines/anio/{anio}/cantViajes/{cantViajes}")
    public ResponseEntity<?> getMonopatinesByCantidadViajesYAnio(@PathVariable Long cantViajes, @PathVariable Long anio) {
        try {
            List<ReporteMonopatinesPorViajesYAnio> reporte = viajeService.getReportePorViajeYAnio(cantViajes, anio);
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Obtener total facturado entre meses de un año")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Datos no encontrados", content = @Content)
    })
    // Obtener reporte total facturado entre meses de un año
    @GetMapping("/facturado")
    public ResponseEntity<?> getReporteTotalFacturadoEntreMesesDeAnio(@RequestParam Long mesInicio,
                                                                      @RequestParam Long mesFin,
                                                                      @RequestParam Long anio) {

        try {
            ReporteTotalFacturadoEntreMesesDeAnio reporte = viajeService.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio);
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
