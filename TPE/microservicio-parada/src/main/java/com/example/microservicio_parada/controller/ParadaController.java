package com.example.microservicio_parada.controller;

import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.models.Monopatin;
import com.example.microservicio_parada.service.ParadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paradas")
public class ParadaController {

    @Autowired
    ParadaService paradaService;
    
    @Operation(summary = "Crear una nueva parada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parada creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    // Create
    @PostMapping("")
    public ResponseEntity<Parada> save(@RequestBody Parada parada) {
        Parada paradaNew = paradaService.save(parada);
        return ResponseEntity.ok(paradaNew);
    }


    @Operation(summary = "Obtener todas las paradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron paradas")
    })
    // Read
    @GetMapping("")
    public ResponseEntity<List<Parada>> getAllParadas() {
        List<Parada> paradas = paradaService.getAll();
        if (paradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @Operation(summary = "Obtener todas las paradas habilitadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron paradas habilitadas")
    })
    @GetMapping("/habilitadas")
    public ResponseEntity<List<Parada>> getAllParadasHabilitadas() {
        List<Parada> paradas = paradaService.getAllHabilitadas();
        if (paradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @Operation(summary = "Obtener todas las paradas deshabilitadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron paradas deshabilitadas")
    })
    @GetMapping("/deshabilitadas")
    public ResponseEntity<List<Parada>> getAllParadasDeshabilitadas() {
        List<Parada> paradas = paradaService.getAllDeshabilitadas();
        if (paradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @Operation(summary = "Obtener una parada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parada obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parada);
    }


    @Operation(summary = "Eliminar una parada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parada eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada", content = @Content)
    })
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        paradaService.delete(parada);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Habilitar una parada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parada habilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada", content = @Content)
    })
    // Habilitar
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<Void> habilitar(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        paradaService.habilitar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deshabilitar una parada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parada deshabilitada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada", content = @Content)
    })
    // Deshabilitar
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<Void> deshabilitar(@PathVariable("id") Long id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return ResponseEntity.notFound().build();
        }
        paradaService.deshabilitar(id);
        return ResponseEntity.noContent().build();
    }

    /*
    3g. Como usuario quiero un listado de los monopatines cercanos a mi zona, para poder encontrar
    un monopatín cerca de mi ubicación.
    */
    @Operation(summary = "Obtener monopatines cercanos a una ubicación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de monopatines cercanos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron monopatines cercanos")
    })
    @GetMapping("/cercanos")
    public ResponseEntity<List<Monopatin>> getMonopatinesCercanos(
            @RequestParam double latitud,
            @RequestParam double longitud,
            @RequestParam double radio) {
        List<Monopatin> monopatinesCercanos = paradaService.getMonopatinesCercanos(latitud, longitud, radio);
        if (monopatinesCercanos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatinesCercanos);
    }

    @Operation(summary = "Quitar un monopatín de una parada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín quitado exitosamente"),
            @ApiResponse(responseCode = "204", description = "Parada no encontrada o no hay monopatín para quitar")
    })
    @PutMapping("/{idParada}/monopatin/{idMonopatin}/quitarMonopatin")
    public ResponseEntity<?> quitarMonopatin(@PathVariable("idParada") Long idParada, @PathVariable Long idMonopatin) {
        Parada parada = paradaService.quitarMonopatin(idParada, idMonopatin);
        if (parada == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parada);
    }
}
