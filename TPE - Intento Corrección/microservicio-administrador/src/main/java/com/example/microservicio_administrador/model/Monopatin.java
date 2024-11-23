package com.example.microservicio_administrador.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {

    private Long id;
    private Long kmRecorridosTotales;
    private Boolean disponible;
    private Long longitud;
    private Long latitud;
    private Long viajeActivo;
}
