package com.example.microservicio_monopatin.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUsoDto {
    private Long idMonopatin;
    private Long kmRecorridos;
    private Long tiempoTotal = 0L;
}
