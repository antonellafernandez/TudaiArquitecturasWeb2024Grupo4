package com.example.microservicio_mantenimiento.controller;

import com.example.microservicio_mantenimiento.service.MantenimientoService;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mantenimiento")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;

    @PutMapping("/registrar/{id}")
    public Monopatin registrarMantenimiento(@PathVariable Long id) {
        return mantenimientoService.registrarMonopatinEnMantenimiento(id);
    }
/*
@PutMapping("/finalizar/{id}")
    public Monopatin finalizarMantenimiento(@PathVariable Long id) {
        Monopatin monopatin = monopatinRepository.findById(id).orElseThrow(() -> new RuntimeException("Monopatín no encontrado"));
        monopatin.setDisponible(true);
        monopatin.setEnMantenimiento(false);
        return monopatinRepository.save(monopatin);
    }



    @GetMapping("/necesita/{id}")
    public boolean necesitaMantenimiento(@PathVariable Long id) {
        return mantenimientoService.necesitaMantenimiento(id);
    }*/
}