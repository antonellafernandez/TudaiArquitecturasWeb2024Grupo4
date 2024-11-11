package com.example.microservicio_viaje.dto;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ViajeDto {
    private Long id;
    private Long idMonopatin;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long kmRecorridos;
    private Double valorTotal;

    private List<LocalDateTime> inicioPausasFinal;
}
