package com.example.microservicio_mantenimiento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "mantenimientoMonopatines", joinColumns = @JoinColumn(name = "mantenimientoId"))
    @Column(name = "idMonopatin")
    private List<Long> idMonopatin;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

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
