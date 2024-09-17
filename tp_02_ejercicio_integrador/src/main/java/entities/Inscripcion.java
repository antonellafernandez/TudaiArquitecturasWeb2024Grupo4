package entities;

import javax.persistence.*;

@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int antiguedad;

    @Column
    private boolean graduado;

    // Relación muchos a uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    // Relación muchos a uno con Estudiante
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    // Constructores
    public Inscripcion() {

    }

    public Inscripcion(int antiguedad, boolean graduado, Carrera carrera, Estudiante estudiante) {
        this.antiguedad = antiguedad;
        this.graduado = graduado;
        this.carrera = carrera;
        this.estudiante = estudiante;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
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
        return "Inscripcion{" +
                "id=" + id +
                ", antiguedad=" + antiguedad +
                ", graduado=" + graduado +
                ", carrera=" + carrera +
                ", estudiante=" + estudiante +
                '}';
    }
}
