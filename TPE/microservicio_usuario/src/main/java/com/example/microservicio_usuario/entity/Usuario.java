package com.example.microserviciousuario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String nro_celular;
    private String email;

    // Relación Uno a Uno con Monopatin
    @OneToOne
    @JoinColumn(name = "monopatin_id")
    private Monopatin monopatin;

    // Relación Muchos a Muchos con CuentaApp
    @ManyToMany
    @JoinTable(
            name = "usuario_cuenta",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "cuenta_id")
    )
    private List<CuentaApp> cuentas;
}
