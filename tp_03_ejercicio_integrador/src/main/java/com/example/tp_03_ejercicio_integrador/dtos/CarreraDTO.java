package com.example.tp_03_ejercicio_integrador.dtos;

import lombok.*;

@Getter
@Setter
@ToString
public class CarreraDTO {
    private String nombre;

    public CarreraDTO() {}

    public CarreraDTO(String nombre) {
        this.nombre = nombre;
    }

}
