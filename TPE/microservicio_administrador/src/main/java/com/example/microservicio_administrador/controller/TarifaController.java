package com.example.microservicio_administrador.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrador/tarifas")
public class TarifaController {

    @GetMapping("/normal")
    public Tarifa getTarifaNormal(){
        
    }
}
