package com.example.microservicio_viaje.controller;

import com.example.microservicio_viaje.dto.ReporteMonopatinesPorViajesYAnio;
import com.example.microservicio_viaje.dto.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    ViajeService viajeService;

    @GetMapping("")
    public ResponseEntity<List<Viaje>> getAllViajes() {
        List<Viaje> viajes = viajeService.getAll();
        if (viajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViajeById(@PathVariable("id") Long id) {
        Viaje viaje = viajeService.findById(id);
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @PostMapping("")
    public ResponseEntity<Viaje> save(@RequestBody Viaje viaje) {
        Viaje viajeNew = viajeService.save(viaje);
        return ResponseEntity.ok(viajeNew);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Viaje viaje = viajeService.findById(id);
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        viajeService.delete(viaje);
        return ResponseEntity.noContent().build();
    }


    // Registrar inicio de pausa
    @PostMapping("/registrarInicioPausa")
    public ResponseEntity<Void> registrarInicioPausa(@RequestParam("id") Long idViaje,
                                                     @RequestParam("fechaHoraInicio") LocalDateTime fechaHoraInicio) {
        viajeService.registrarInicioPausa(idViaje, fechaHoraInicio);
        return ResponseEntity.ok().build();
    }

    // Obtener el inicio de la última pausa
    @GetMapping("/obtenerInicioUltimaPausa")
    public ResponseEntity<LocalDateTime> obtenerInicioUltimaPausa(@RequestParam("monopatinId") Long monopatinId) {
        LocalDateTime inicioUltimaPausa = viajeService.obtenerInicioUltimaPausa(monopatinId);
        return ResponseEntity.ok(inicioUltimaPausa);
    }

    // Registrar fin de pausa
    @PostMapping("/registrarFinPausa")
    public ResponseEntity<Void> registrarFinPausa(@RequestParam("monopatinId") Long monopatinId,
                                                  @RequestParam("fechaHoraFin") LocalDateTime fechaHoraFin) {
        viajeService.registrarFinPausa(monopatinId, fechaHoraFin);
        return ResponseEntity.ok().build();
    }

    // Finalizar viaje
    @PutMapping("/finalizar")
    public ResponseEntity<Void> finalizarViaje(@RequestParam("viajeId") Long viajeId,
                                               @RequestParam("fechaHoraFin") LocalDateTime fechaHoraFin,
                                               @RequestParam("kmRecorridos") Long kmRecorridos) {
        viajeService.finalizarViaje(viajeId, fechaHoraFin, kmRecorridos);
        return ResponseEntity.ok().build();
    }

    // Iniciar viaje
    @PostMapping("/iniciar")
    public ResponseEntity<Void> iniciarViaje(@RequestParam Long monopatinId,
                                             @RequestParam LocalDateTime fechaHoraInicio) {
        viajeService.iniciarViaje(monopatinId, fechaHoraInicio);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/totalPausas")
    public ResponseEntity<?> getPausasMonopatines(){
        try {
            Map<Long, Long> tiempoPausas = viajeService.getDuracionPausas();
            return ResponseEntity.status(HttpStatus.OK).body(tiempoPausas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/monopatines/anio/{anio}/cantViajes/{cantViajes}")
    public ResponseEntity<?> getMonopatinesByCantidadViajesYAnio(@PathVariable Long cantViajes, @PathVariable Long anio){
        try {
        List<ReporteMonopatinesPorViajesYAnio> reporte = viajeService.getReportePorViajeYAnio(cantViajes, anio);
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/facturado")
    public ResponseEntity<?> getReporteTotalFacturadoEntreMesesDeAnio(@RequestParam Long mesInicio,
                                                                      @RequestParam Long mesFin,
                                                                        @RequestParam Long anio){

        try {
            ReporteTotalFacturadoEntreMesesDeAnio reporte = viajeService.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio);
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
