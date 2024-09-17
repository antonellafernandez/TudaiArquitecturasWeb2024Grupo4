package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsContructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;
    private String apellido;
    private int edad;
    private String genero;
    private String numeroDocumento;
    private String ciudadResidencia;
    private String numeroLibretaUniversitaria;

    //esto crea la tabla intermedia entre carrera y estudiante
    @ManyToMany
    @JoinTable(
            name = "estudiantes_carreras",
            joinColumns = @JoinColumn(name = "estudiante_id"),
            inverseJoinColumns = @JoinColumn(name = "carrera_id")
    )
    private Set<Carrera> carreras;
    private boolean graduado;
}
