package com.example.microservicio_administrador.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    private String nombre;
    private Long longitud;
    private Long latitud;
    private List<Long> idMonopatines;
}
