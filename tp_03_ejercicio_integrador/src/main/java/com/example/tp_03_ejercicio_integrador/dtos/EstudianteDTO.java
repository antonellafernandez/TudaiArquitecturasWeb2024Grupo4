package com.example.tp_03_ejercicio_integrador.dtos;

import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class EstudianteDTO {
    private String nombre;
    private String apellido;
    private int edad;
    private int dni;
    private String genero;
    private String ciudadResidencia;
    private Long lu;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<EstudianteCarrera> inscripciones;

    public EstudianteDTO() {
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public EstudianteDTO(String nombre, String apellido, int edad, int dni, String genero, String ciudadResidencia, Long lu) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.genero = genero;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
        this.inscripciones = new ArrayList<>();
    }

    public List<EstudianteCarrera> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(EstudianteCarrera inscripcion) {
        if (!inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }

    public void removeInscripcion(EstudianteCarrera inscripcion) {
        inscripciones.remove(inscripcion);
    }
}
