package dtos;

import entities.Carrera;
import entities.Estudiante;

import java.time.LocalDate;

public class InscripcionDTO {
    private int antiguedad;
    private LocalDate anioInscripcion;
    private LocalDate anioEgreso;
    private boolean graduado;
    private Carrera carrera;
    private Estudiante estudiante;

    public InscripcionDTO() {}

    public InscripcionDTO(int antiguedad, LocalDate anioInscripcion, LocalDate anioEgreso, boolean graduado, Carrera carrera, Estudiante estudiante) {
        this.antiguedad = antiguedad;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.graduado = graduado;
        this.carrera = carrera;
        this.estudiante = estudiante;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public LocalDate getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(LocalDate anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public LocalDate getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(LocalDate anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "InscripcionDTO{" +
                "antiguedad=" + antiguedad +
                ", anioInscripcion=" + anioInscripcion +
                ", anioEgreso=" + anioEgreso +
                ", graduado=" + graduado +
                ", carrera=" + carrera +
                ", estudiante=" + estudiante +
                '}';
    }
}
