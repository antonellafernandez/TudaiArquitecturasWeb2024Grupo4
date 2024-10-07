package com.example.tp_03_ejercicio_integrador.modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Estudiante {
    @Id
    private int dni;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int edad;

    @Column
    private String genero;

    @Column
    private String ciudadResidencia;

    @Column
    private Long lu;

    // Relación uno a muchos con la entidad Inscripcion
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // CascadeType.ALL significa que todas las operaciones de cascada se aplicarán a las entidades relacionadas
    private List<EstudianteCarrera> inscripciones;

    public Estudiante() {
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public Estudiante(int dni, String nombre, String apellido, int edad, String genero,
                      String ciudadResidencia, Long lu) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public Long getLu() {
        return lu;
    }

    public void setLu(Long lu) {
        this.lu = lu;
    }

    public List<EstudianteCarrera> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(EstudianteCarrera estudianteCarrera) {
        if (!inscripciones.contains(estudianteCarrera)) {
            inscripciones.add(estudianteCarrera);
            estudianteCarrera.setEstudiante(this); // Mantener la relación bidireccional
        }
    }

    public void removeInscripcion(EstudianteCarrera estudianteCarrera) {
        if (inscripciones.contains(estudianteCarrera)) {
            inscripciones.remove(estudianteCarrera);
            estudianteCarrera.setEstudiante(null); // Mantener la relación bidireccional
        }
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", ciudadResidencia='" + ciudadResidencia + '\'' +
                ", lu=" + lu +
                ", inscripciones=" + inscripciones +
                '}';
    }
}
