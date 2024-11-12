package com.example.microservicio_monopatin.entity;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
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
    private Long tiempoRecorridosTotales;
    private Boolean disponible;
    private Long longitud;
    private Long latitud;
    private Long idViajeActivo;

    public Monopatin(MonopatinDTO monopatinDTO) {
    }

    public boolean isDisponible() {
        return this.disponible;
    }
}