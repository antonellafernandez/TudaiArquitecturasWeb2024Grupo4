package com.example.microservicio_parada.entity;

import com.example.microservicio_monopatin.entity.Monopatin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ubicacion;
    @OneToMany(mappedBy = "parada")
    private List<Monopatin> monopatines;

    private String nombre;
    private Long longitud;
    private Long latitud;

    @ElementCollection
    @CollectionTable(name = "paradaMonopatines", joinColumns = @JoinColumn(name = "paradaId"))
    @Column(name = "idMonopatin")
    private List<Long> idMonopatines;
z

    private Boolean habilitado;

    // Método para agregar un monopatín por su ID
    public void agregarMonopatin(Long monopatinId) {
        if (!idMonopatines.contains(monopatinId)) {
            idMonopatines.add(monopatinId);
        }
    }

    // Método para quitar un monopatín por su ID
    public void quitarMonopatin(Long monopatinId) {
        idMonopatines.remove(monopatinId);
    }
}
