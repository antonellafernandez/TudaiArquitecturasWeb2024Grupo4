package com.example.tp_03_ejercicio_integrador.dtos;

import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
