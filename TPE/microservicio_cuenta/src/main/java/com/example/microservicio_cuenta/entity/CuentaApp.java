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
    private Long id;
    private LocalDate fechaAlta;
    private Long montoCreditos;
    private Long idCuentaMp;

    @ElementCollection
    @CollectionTable(name = "cuentaAppUsuarios", joinColumns = @JoinColumn(name = "cuentaAppId"))
    @Column(name = "idUsuario")
    private List<Long> idUsuarios;

    private Boolean habilitado;

    // Método para agregar un usuario por su ID
    public void agregarUsuario(Long usuarioId) {
        if (!idUsuarios.contains(usuarioId)) {
            idUsuarios.add(usuarioId);
        }
    }

    // Método para quitar un usuario por su ID
    public void quitarUsuario(Long usuarioId) {
        idUsuarios.remove(usuarioId);
    }
}
