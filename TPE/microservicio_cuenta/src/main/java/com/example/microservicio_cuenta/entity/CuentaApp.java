package com.example.microservicio_cuenta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    private LocalDate fechaAlta;
    private Long montoCreditos;
    private int idCuentaMP;

    @ManyToMany(mappedBy = "cuentas")
    private List<Usuario> usuarios;

    @ManyToOne
    @JoinColumn(name = "id_cuenta_mp", insertable = false, updatable = false)
    private CuentaMercadoPago cuentaMercadoPago;
}
