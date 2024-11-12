package com.example.microservicio_usuario.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long kmRecorridos;
}