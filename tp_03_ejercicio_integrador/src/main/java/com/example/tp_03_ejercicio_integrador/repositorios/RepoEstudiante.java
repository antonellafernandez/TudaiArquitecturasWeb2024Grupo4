package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.model.Estudiante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RepoEstudiante")
public interface RepoEstudiante extends RepoBase<Estudiante, Long> {

    //2.C
    @Query("SELECT e FROM Estudiante e WHERE e.apellido = :apellido")
    List<Estudiante> getEstudiantesByApellido(String apellido);

    //2.D
    @Query("SELECT e FROM Estudiante e WHERE e.lu = :lu")
    List<Estudiante> getEstudianteByLu(String lu);

    //2.E
    @Query("SELECT e FROM Estudiante e WHERE e.genero = :genero")
    List<Estudiante> getEstudianteByGenero(String genero);

    //2.G
    @Query("SELECT e " +
            "FROM Estudiante e " +
            "JOIN e.carrera c " +
            "WHERE e.ciudadResidencia = :ciudadResidencia " +
            "AND c.nombre LIKE :nombreCarrera")
    List<Estudiante> getEstudiantesByCiudad(String ciudadResidencia, String nombreCarrera);

}
