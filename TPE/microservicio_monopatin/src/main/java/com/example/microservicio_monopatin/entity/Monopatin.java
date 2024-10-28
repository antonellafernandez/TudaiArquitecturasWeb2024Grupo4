package com.example.microserviciomonopatin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMonopatin;

    @OneToOne(cascade = CascadeType.ALL)
    private Gps gps;

    private long kmRecorridosTotales;
    private boolean disponible;  // Para saber si está disponible para su uso

    @ManyToOne
    @JoinColumn(name = "id_viaje")
    private Viaje viaje;  // Relación con la entidad Viaje

    @ManyToOne
    @JoinColumn(name = "id_parada")
    private Parada parada; // Relación muchos a uno con Parada

    public void pausarMonopatin() {
        this.disponible = false;  // Cambia el estado a no disponible
    }
}
