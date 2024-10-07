package com.example.tp_03_ejercicio_integrador.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstudianteCarreraDTO {
    private Long idEstudiante;
    private Long idCarrera;
    private int anioInscripcion;
    private Integer anioEgreso; // Puede ser null
    private int antiguedad;
    private boolean graduado;

    public EstudianteCarreraDTO(Long idEstudiante, Long idCarrera, int anioInscripcion, Integer anioEgreso, int antiguedad, boolean graduado) {
        this.idEstudiante = idEstudiante;
        this.idCarrera = idCarrera;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.antiguedad = antiguedad;
        this.graduado = graduado;
    }
}