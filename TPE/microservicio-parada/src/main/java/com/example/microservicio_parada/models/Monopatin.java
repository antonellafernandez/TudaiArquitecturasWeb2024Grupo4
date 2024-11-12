package com.example.microservicio_parada.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private Long gpsId;
    private Long kmRecorridosTotales;
    private Long tiempoRecorridosTotales;
    private Boolean disponible;
    private Long longitud;
    private Long latitud;
    private Long idViajeActivo;
}
