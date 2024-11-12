package com.example.microservicio_usuario.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaApp {
    private LocalDate fechaAlta;
    private Long montoCreditos;
    private Long idCuentaMp;
    private List<Long> idUsuarios;
    private Boolean habilitado;
}
