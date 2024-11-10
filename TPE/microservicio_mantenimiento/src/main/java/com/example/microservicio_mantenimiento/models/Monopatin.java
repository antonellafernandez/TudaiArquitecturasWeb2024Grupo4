package com.example.microservicio_mantenimiento.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private Long gpsId;
    private Long kmRecorridosTotales;
    private Boolean disponible;
    private Long longitud;
    private Long latitud;
    private Long idViajeActivo;
}
