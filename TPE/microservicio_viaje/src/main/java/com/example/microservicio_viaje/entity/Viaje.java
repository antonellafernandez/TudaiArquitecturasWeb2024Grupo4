package com.example.microservicio_viaje.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idViaje;

    // Lista para almacenar el inicio, pausas y final del viaje
    @ElementCollection
    private List<LocalDateTime> inicioPausasFinal = new ArrayList<>();

    private long kmRecorridos; // Kilómetros recorridos en este viaje

    @ManyToOne
    @JoinColumn(name = "id_monopatin")
    private Monopatin monopatin;
}
