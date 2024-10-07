package com.example.tp_03_ejercicio_integrador.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReporteCarreraDTO {
    private String nombreCarrera;
    private int anioInscripcion;
    private int anioEgreso;
    private long cantidadInscriptos;
    private long cantidadEgresados;
    private Long luEstudiante;
}
