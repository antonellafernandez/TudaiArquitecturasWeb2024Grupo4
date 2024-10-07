package com.example.tp_03_ejercicio_integrador.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EstudianteCarreraDTO {
    private int idEstudiante;
    private int idCarrera;
    private int anioInscripcion;
    private int anioEgreso; // Puede ser null
    private int antiguedad;
    private boolean graduado;
}
