package entities;

import javax.persistence.*;
import java.util.Set;

@AllArgsContructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "carreras")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "carreras")
    private Set<Estudiante> estudiantes;

}

