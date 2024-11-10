package com.example.microservicio_monopatin.entity;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import com.example.microservicio_parada.entity.Parada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long kmRecorridosTotales;
    private Boolean disponible;
    private Long longitud;
    private Long latitud;
    private Long viajeActivo;

    @OneToMany
    private List<Parada> paradasValidas;

    public Monopatin(MonopatinDTO monopatinDTO) {
    }

    public boolean isDisponible() {
        return this.disponible;
    }
}