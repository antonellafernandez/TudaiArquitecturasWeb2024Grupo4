package com.example.microservicio_parada.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "paradas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parada {

    @Id
    private String id;

    private String ubicacion;
    private String nombre;
    private Long longitud;
    private Long latitud;

    private List<Long> idMonopatines;

    private Boolean habilitado;

    // Método para agregar un monopatín por su ID
    public void agregarMonopatin(Long monopatinId) {
        if (!idMonopatines.contains(monopatinId)) {
            idMonopatines.add(monopatinId);
        }
    }

    // Método para quitar un monopatín por su ID
    public void quitarMonopatin(Long monopatinId) {
        idMonopatines.remove(monopatinId);
    }
}
