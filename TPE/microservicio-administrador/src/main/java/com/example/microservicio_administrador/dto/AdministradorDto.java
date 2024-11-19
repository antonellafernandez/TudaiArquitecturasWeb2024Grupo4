package com.example.microservicio_administrador.dto;

import com.example.microservicio_administrador.entity.Administrador;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorDto {

    @NotNull
    private String nombre;
   
    private Long id;

    public AdministradorDto(Administrador admin) {
        this.nombre = admin.getNombre();
    }
}
