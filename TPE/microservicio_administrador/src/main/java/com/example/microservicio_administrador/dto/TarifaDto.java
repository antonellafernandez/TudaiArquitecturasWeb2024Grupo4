package com.example.microservicio_administrador.dto;

import com.example.microservicio_administrador.entity.Tarifa;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TarifaDto {
    private String nombreTarifa;
    private String tipoTarifa;
    private Double precioTarifa;
    private Double descuentoTarifa;
    private LocalDate fechaInicio;

    public TarifaDto(Tarifa tarifa) {
        nombreTarifa = tarifa.getNombreTarifa();
        tipoTarifa = tarifa.getTipoTarifa();
        precioTarifa = tarifa.getPrecioTarifa();
        descuentoTarifa = tarifa.getDescuentoTarifa();
        fechaInicio = tarifa.getFechaInicio();
    }
}
