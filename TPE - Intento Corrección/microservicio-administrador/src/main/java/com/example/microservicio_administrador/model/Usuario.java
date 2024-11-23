package com.example.microservicio_administrador.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private String nombre;
    private String apellido;
    private String nroCelular;
    private String email;

    private List<Long> idCuentaApps;

    private Boolean habilitado;
}
