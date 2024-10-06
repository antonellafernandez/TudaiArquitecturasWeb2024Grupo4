package com.example.tp_03_ejercicio_integrador.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrera {
    @Id
    private int id;

    @Column(nullable = false)
    private String nombre;

    // Relación uno a muchos con la entidad Inscripcion
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // CascadeType.ALL significa que todas las operaciones de cascada se aplicarán a las entidades relacionadas
    private List<EstudianteCarrera> inscripciones;

    public Carrera() {
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public Carrera(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<EstudianteCarrera> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(EstudianteCarrera estudianteCarrera) {
        if (!inscripciones.contains(estudianteCarrera)) {
            inscripciones.add(estudianteCarrera);
            estudianteCarrera.setCarrera(this); // Mantener la relación bidireccional
        }
    }

    public void removeInscripcion(EstudianteCarrera estudianteCarrera) {
        if (inscripciones.contains(estudianteCarrera)) {
            inscripciones.remove(estudianteCarrera);
            estudianteCarrera.setCarrera(null); // Mantener la relación bidireccional
        }
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", inscripciones=" + inscripciones +
                '}';
    }
}
