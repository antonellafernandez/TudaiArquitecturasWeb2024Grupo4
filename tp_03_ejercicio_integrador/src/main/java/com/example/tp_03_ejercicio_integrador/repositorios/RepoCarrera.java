package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.model.Carrera;
import com.example.tp_03_ejercicio_integrador.model.Estudiante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RepoCarrera")
public interface RepoCarrera extends RepoBase<Estudiante, Long> {

    // 2h) Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y
    // presentar los años de manera cronológica.
    @Query("SELECT new dtos.ReporteCarreraDTO(c.nombre, " +
            "YEAR(i.anioInscripcion), " +
            "YEAR(i.anioEgreso), " +
            "(SELECT COUNT(i1) FROM Inscripcion i1 WHERE i1.anioEgreso IS NULL AND i1.carrera = c), " + // Inscripciones sin egreso
            "(SELECT COUNT(i2) FROM Inscripcion i2 WHERE i2.anioEgreso IS NOT NULL AND i2.carrera = c), " + // Inscripciones con egreso
            "e.id) " +
            "FROM EstudianteCarrera i " +
            "JOIN i.carrera c " +
            "JOIN i.estudiante e " +
            "ORDER BY c.nombre ASC, YEAR(i.anioInscripcion) ASC, YEAR(i.anioEgreso) ASC")
    List<Carrera> getReporteCarreras();

}
