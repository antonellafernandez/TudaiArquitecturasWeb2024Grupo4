package com.example.microservicio_cuenta.models;

import java.util.List;

public class Usuario {
    private String nombre;
    private String apellido;
    private String nroCelular;
    private String email;
    private Long idMonopatinActual;
    private List<Long> idCuentaApps;
    private Boolean habilitado;
}
