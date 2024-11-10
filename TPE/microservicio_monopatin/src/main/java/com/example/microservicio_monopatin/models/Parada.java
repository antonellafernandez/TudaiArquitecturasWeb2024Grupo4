package com.example.microservicio_monopatin.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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