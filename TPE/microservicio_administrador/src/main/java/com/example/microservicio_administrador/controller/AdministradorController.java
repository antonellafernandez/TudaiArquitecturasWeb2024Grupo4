package com.example.microservicio_administrador.controller;


import com.example.microservicio_administrador.dto.AdministradorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/")
    public ResponseEntity<List<AdministradorResponseDto>> getAllAdministradores(){
        List<AdministradorResponseDto> administradores = administradorService.getAll();
        if(administradores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponseDto> getAdministradorById(@PathVariable Long id){
        AdministradorResponseDto admin = administradorService.getAdministradorById(id);
        if(admin == null){
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(admin);
    }
}
