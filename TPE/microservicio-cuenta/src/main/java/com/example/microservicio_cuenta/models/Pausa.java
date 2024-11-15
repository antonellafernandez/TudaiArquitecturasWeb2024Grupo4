package com.example.microservicio_cuenta.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pausa {
    private Long id;

    private LocalDateTime pausa;

    private Viaje viaje;

    public Pausa(LocalDateTime pausa, Viaje viaje){
        this.pausa = pausa;
        this.viaje = viaje;
    }
}
