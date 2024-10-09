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
            "GROUP BY c.nombre " +
            "ORDER BY COUNT(ec) DESC"
    )
    List<CarreraConCantInscriptosDTO> getCarrerasOrdenadasPorInscriptos();

    // 2h) Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y
    // presentar los años de manera cronológica.
    @Query("SELECT new com.example.tp_03_ejercicio_integrador.dtos.ReporteCarreraDTO(" +
            "c.nombre, " +
            "ec.anioInscripcion, " +
            "ec.anioEgreso, " +
            "(SELECT COUNT(ec1) FROM EstudianteCarrera ec1 WHERE ec1.anioEgreso = 0 AND ec1.carrera = c), " + // Inscripciones sin egreso
            "(SELECT COUNT(ec2) FROM EstudianteCarrera ec2 WHERE ec2.anioEgreso != 0 AND ec2.carrera = c), " + // Inscripciones con egreso
            "e.lu) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "JOIN ec.estudiante e " +
            "ORDER BY c.nombre ASC, ec.anioInscripcion ASC, ec.anioEgreso ASC")
    List<ReporteCarreraDTO> getReporteCarreras();

}
