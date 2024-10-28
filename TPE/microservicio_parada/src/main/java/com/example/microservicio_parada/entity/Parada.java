package com.example.microservicio_parada.entity;

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

/*
    // Método para agregar un monopatín
    public void agregarMonopatin(Monopatin monopatin) {
        if (!monopatines.contains(monopatin)) {
            monopatines.add(monopatin);
            monopatin.setParada(this);  // Establece la relación bidireccional
        }
    }

    // Método para quitar un monopatín
    public void quitarMonopatin(Monopatin monopatin) {
        if (monopatines.contains(monopatin)) {
            monopatines.remove(monopatin);
            monopatin.setParada(null);  // Elimina la relación bidireccional
        }
    }

 */
}