package com.example.microservicio_cuenta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaAlta;
    private Long montoCreditos;
    private Long idCuentaMp;

    @ManyToMany(mappedBy = "cuentas")
    private List<Usuario> usuarios;

    private Boolean habilitado;
}
