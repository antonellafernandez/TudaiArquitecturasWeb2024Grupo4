package com.example.microservicio_viaje.dto;

import com.example.microservicio_viaje.entity.Pausa;
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
    private LocalDateTime pausa;

    public ReporteUsoPorTiempoDto(Long idMonopatin, Pausa pausa) {
        this.idMonopatin = idMonopatin;
        this.pausa = pausa.getPausa();
    }
}
