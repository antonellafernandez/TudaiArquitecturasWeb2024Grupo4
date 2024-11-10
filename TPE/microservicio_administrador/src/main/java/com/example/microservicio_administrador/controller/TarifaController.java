package com.example.microservicio_administrador.controller;

import com.example.microservicio_administrador.dto.TarifaDto;
import com.example.microservicio_administrador.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrador/tarifas")
public class TarifaController {

    @Autowired
    TarifaService tarifaService;

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<TarifaDto> getTarifaByTipo(@PathVariable String tipo) {
        TarifaDto tarifaDto = tarifaService.getTarifaByTipo(tipo);
        if (tarifaDto != null)
            return ResponseEntity.ok(tarifaDto);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<TarifaDto>> getTarifas() {
        List<TarifaDto> tarifas = tarifaService.getTarifas();
        if (tarifas != null && tarifas.size() > 0)
            return ResponseEntity.ok(tarifas);

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<TarifaDto> save(@RequestBody TarifaDto tarifaDto) {
        TarifaDto tarifa = tarifaService.save(tarifaDto);
        if (tarifa != null)
            return ResponseEntity.ok(tarifa);

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaDto> update(@PathVariable Long id, @RequestBody TarifaDto tarifaDto) {
        TarifaDto tarifa = tarifaService.update(id, tarifaDto);
        if (tarifa != null)
            return ResponseEntity.ok(tarifa);

        return ResponseEntity.notFound().build();
    }
}
