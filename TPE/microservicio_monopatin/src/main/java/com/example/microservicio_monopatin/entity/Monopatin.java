package com.example.microservicio_monopatin.entity;

import com.example.microservicio_viaje.entity.Viaje;
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
    private Long id;
    private Long gpsId;
    private Long kmRecorridosTotales;
    private Boolean disponible;
    private Boolean longitud;
    private Boolean latitud;

    @OneToOne(mappedBy = "monopatin")
    private Viaje viajeActivo;

}