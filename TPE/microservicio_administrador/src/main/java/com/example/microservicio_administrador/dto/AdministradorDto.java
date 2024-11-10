package com.example.microservicio_administrador.dto;

import com.example.microservicio_administrador.entity.Administrador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorDto {

    @NotNull
    private String nombre;

    public AdministradorDto(Administrador admin){
        this.nombre = admin.getNombre();
    }
}
