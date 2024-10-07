package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoEstudianteCarrera extends JpaRepository<EstudianteCarrera, Integer> {

    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @Query("SELECT ec, COUNT(ec.id_estudiante) AS cantidad_inscriptos " +
            "FROM Carrera c " +
            "JOIN c.estudianteCarrera ec " +
            "GROUP BY c " +
            "ORDER BY cantidad_inscriptos DESC"
    )
    List<Carrera> getCarrerasConEstudiantesOrdenadas();
}
