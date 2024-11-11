package com.example.microservicio_viaje.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUsoPorKilometro {
    private Long idMonopatin;
    private Long kmRecorridos;
}
