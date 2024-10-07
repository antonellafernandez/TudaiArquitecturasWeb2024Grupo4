package com.example.tp_03_ejercicio_integrador.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EstudianteCarreraDTO {
    private Long luEstudiante;
    private String nombreCarrera;
    private int anioInscripcion;
    private int anioEgreso;
    private int antiguedad;
    private boolean graduado;
}
