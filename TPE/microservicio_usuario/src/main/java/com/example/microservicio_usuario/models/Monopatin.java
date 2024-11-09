package com.example.microservicio_usuario.models;

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
    private Boolean longitud;
    private Boolean latitud;
}
