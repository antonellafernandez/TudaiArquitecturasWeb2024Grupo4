package com.example.microservicio_administrador.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombreTarifa;
    @Column(nullable = false)
    private String tipoTarifa;
    @Column(nullable = false)
    private Double precioTarifa;
    private Double descuentoTarifa;
}
