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
    private Long idMonopatin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Mantenimiento(Long idMonopatin, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.idMonopatin = idMonopatin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
