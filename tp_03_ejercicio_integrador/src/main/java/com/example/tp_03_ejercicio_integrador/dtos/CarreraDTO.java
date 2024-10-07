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

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<EstudianteCarrera> inscripciones;

    public CarreraDTO() {
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public CarreraDTO(String nombre) {
        this.nombre = nombre;
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
