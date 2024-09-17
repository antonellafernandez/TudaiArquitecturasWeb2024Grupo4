package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String nombre;

    // Relación uno a muchos con la entidad Inscripcion
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // CascadeType.ALL significa que todas las operaciones de cascada se aplicarán a las entidades relacionadas
    private List<Inscripcion> inscripciones;

    // Constructores
    public Carrera() {
        this.inscripciones = new ArrayList<Inscripcion>();
    }

    public Carrera(String nombre) {
        this.nombre = nombre;
        this.inscripciones = new ArrayList<Inscripcion>();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Inscripcion> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    /*
    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
     */

    @Override
    public String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", inscripciones=" + inscripciones +
                '}';
    }
}
