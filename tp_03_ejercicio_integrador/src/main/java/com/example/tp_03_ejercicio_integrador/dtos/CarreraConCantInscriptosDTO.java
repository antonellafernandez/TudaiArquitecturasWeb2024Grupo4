package com.example.tp_03_ejercicio_integrador.dtos;

import lombok.Data;

@Data
public class CarreraConCantInscriptosDTO {
    private String nombreCarrera;
    private Long cantInscriptos;

    public CarreraConCantInscriptosDTO(String nombreCarrera, Long cantInscriptos) {
        this.nombreCarrera = nombreCarrera;
        this.cantInscriptos = cantInscriptos;
    }
}


