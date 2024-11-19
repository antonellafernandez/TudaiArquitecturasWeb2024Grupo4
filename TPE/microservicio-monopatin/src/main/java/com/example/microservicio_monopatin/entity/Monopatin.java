package com.example.microservicio_monopatin.entity;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long kmRecorridosTotales;
    private Long tiempoRecorridosTotales;
    private Boolean disponible = true;
    private Long longitud;
    private Long latitud;
    private Long idViajeActivo;

    public Monopatin(MonopatinDTO monopatinDTO) {
        this.kmRecorridosTotales = monopatinDTO.getKmRecorridosTotales();
        this.tiempoRecorridosTotales = monopatinDTO.getTiempoRecorridosTotales();
        this.disponible = monopatinDTO.getDisponible();
        this.longitud = monopatinDTO.getLongitud();
        this.latitud = monopatinDTO.getLatitud();
        this.idViajeActivo = monopatinDTO.getViajeActivo();
    }
}