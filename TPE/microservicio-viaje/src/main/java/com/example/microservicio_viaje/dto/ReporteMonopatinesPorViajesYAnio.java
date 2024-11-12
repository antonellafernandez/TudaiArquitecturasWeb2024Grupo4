package com.example.microservicio_viaje.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteMonopatinesPorViajesYAnio {
    private Long idMonopatin;
    private Long cantViajes;
    private Long anio;
}
