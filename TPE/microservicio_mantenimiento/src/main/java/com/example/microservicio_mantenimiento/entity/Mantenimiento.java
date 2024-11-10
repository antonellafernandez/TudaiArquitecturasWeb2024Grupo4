package com.example.microservicio_mantenimiento.entity;

import com.example.microservicio_monopatin.entity.Monopatin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Revisar
    @ManyToOne
    @JoinColumn(name = "monopatin_id", nullable = false)
    private Long idMonopatin;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
