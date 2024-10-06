package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.model.Carrera;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoEstudianteCarrera extends RepoBase<Carrera, Long> {

    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @Query("SELECT ec, COUNT(ec.id_estudiante) AS cantidad_inscriptos " +
            "FROM Carrera c " +
            "JOIN c.estudianteCarrera ec " +
            "GROUP BY c " +
            "ORDER BY cantidad_inscriptos DESC"
    )
    List<Carrera> getCarrerasConEstudiantesOrdenadas();
}
