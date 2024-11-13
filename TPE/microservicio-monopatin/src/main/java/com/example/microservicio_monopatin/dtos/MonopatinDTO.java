package com.example.microservicio_monopatin.dtos;


import com.example.microservicio_monopatin.entity.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MonopatinDTO {

    private Long id;

    private Long kmRecorridosTotales;
    private Long tiempoRecorridosTotales;
    private Boolean disponible;
    private Long longitud;
    private Long latitud;
    private Long viajeActivo;

    // Constructor que convierte la entidad Monopatin a MonopatinDTO
    public MonopatinDTO(Monopatin monopatin) {
        this.id = monopatin.getId();
        this.kmRecorridosTotales = monopatin.getKmRecorridosTotales();
        this.tiempoRecorridosTotales = monopatin.getTiempoRecorridosTotales();
        this.disponible = monopatin.getDisponible();
        this.longitud = monopatin.getLongitud();
        this.latitud = monopatin.getLatitud();
        this.viajeActivo = monopatin.getIdViajeActivo();
    }
}
