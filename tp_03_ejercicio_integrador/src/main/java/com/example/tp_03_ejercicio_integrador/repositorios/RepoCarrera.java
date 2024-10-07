package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.model.Carrera;
import com.example.tp_03_ejercicio_integrador.model.Estudiante;
import dtos.ReporteCarreraDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RepoCarrera")
public interface RepoCarrera extends RepoBase<Carrera, Long> {

    // 2h) Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y
    // presentar los años de manera cronológica.
    @Query("SELECT new dtos.ReporteCarreraDTO(c.nombre, " +
            "YEAR(ec.anioInscripcion), " +
            "YEAR(ec.anioEgreso), " +
            "(SELECT COUNT(ec1) FROM EstudianteCarrera ec1 WHERE ec1.anioEgreso IS NULL AND ec1.carrera = c), " + // Inscripciones sin egreso
            "(SELECT COUNT(ec2) FROM EstudianteCarrera ec2 WHERE ec2.anioEgreso IS NOT NULL AND ec2.carrera = c), " + // Inscripciones con egreso
            "e.dni) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "JOIN ec.estudiante e " +
            "ORDER BY c.nombre ASC, YEAR(ec.anioInscripcion) ASC, YEAR(ec.anioEgreso) ASC")
    List<ReporteCarreraDTO> getReporteCarreras();

}
