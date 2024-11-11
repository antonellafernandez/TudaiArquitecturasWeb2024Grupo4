package com.example.microservicio_viaje.dto;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUsoPorTiempoDto {
    private Long idMonopatin;
    private List<LocalDateTime> pausas;
    private Long pausasTotales;

    public ReporteUsoPorTiempoDto(Long idMonopatin, List<LocalDateTime> pausas) {
        this.idMonopatin = idMonopatin;
        this.pausas = pausas;
    }
}
