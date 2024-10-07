package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.dtos.CarreraConCantInscriptosDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.dtos.ReporteCarreraDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RepoCarrera")
public interface RepoCarrera extends JpaRepository<Carrera, Integer> {

    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @Query("SELECT new com.example.tp_03_ejercicio_integrador.dtos.CarreraConCantInscriptosDTO(" +
            "c.nombre, COUNT(ec)) " +
            "FROM Carrera c " +
            "JOIN c.inscripciones ec " +
            "ORDER BY COUNT(ec) DESC"
    )
    List<CarreraConCantInscriptosDTO> getCarrerasOrdenadasPorInscriptos();

    // 2h) Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y
    // presentar los años de manera cronológica.
    @Query("SELECT new com.example.tp_03_ejercicio_integrador.dtos.ReporteCarreraDTO(c.nombre, " +
            "YEAR(ec.anioInscripcion), " +
            "YEAR(ec.anioEgreso), " +
            "(SUM(CASE WHEN ec.anioEgreso = 0 THEN 1 ELSE 0 END)), " + // Inscripciones sin egreso
            "(SUM(CASE WHEN ec.anioEgreso != 0 THEN 1 ELSE 0 END)), " + // Inscripciones con egreso
            "e.dni) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "JOIN ec.estudiante e " +
            "ORDER BY c.nombre ASC, YEAR(ec.anioInscripcion) ASC, YEAR(ec.anioEgreso) ASC")
    List<ReporteCarreraDTO> getReporteCarreras();

}
