package com.example.microservicio_gps.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
/*
    @OneToMany(cascade = CascadeType.ALL)
    private List<Parada> paradasValidas = new ArrayList<>();

    // Método para validar si una parada es permitida
    public boolean validarParadaPermitida(Parada parada) {
        return paradasValidas.contains(parada);
    }

    // Método para registrar una nueva parada permitida
    public void registrarParada(Parada parada) {
        if (!paradasValidas.contains(parada)) {
            paradasValidas.add(parada);
        }
    }

    // Método para quitar una parada permitida
    public void quitarParada(Parada parada) {
        paradasValidas.remove(parada);
    }*/
}