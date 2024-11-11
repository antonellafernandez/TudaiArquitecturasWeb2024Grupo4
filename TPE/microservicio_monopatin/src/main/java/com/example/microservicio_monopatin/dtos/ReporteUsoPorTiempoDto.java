package com.example.microservicio_monopatin.dtos;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUsoPorTiempoDto {
    private Long idMonopatin;
    private Long tiempoTotal;
}
