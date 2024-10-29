package com.example.microservicio_mantenimiento.entity;

import com.example.microservicio_monopatin.entity.Monopatin;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Revisar
    @ManyToOne
    @JoinColumn(name = "monopatin_id", nullable = false)
    private Monopatin monopatin;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    // Constructor, Getters y Setters

    public Mantenimiento() {
    }

    public Mantenimiento(Monopatin monopatin, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.monopatin = monopatin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Monopatin getMonopatin() {
        return monopatin;
    }

    public void setMonopatin(Monopatin monopatin) {
        this.monopatin = monopatin;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}