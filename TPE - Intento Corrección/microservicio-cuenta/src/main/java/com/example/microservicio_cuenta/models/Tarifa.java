package com.example.microservicio_cuenta.models;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {
    private String nombreTarifa;
    private String tipoTarifa;
    private Double precioTarifa;
    private Double descuentoTarifa;
    private LocalDate fechaInicio;
}