package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoEstudianteCarrera extends JpaRepository<EstudianteCarrera, Integer> {


}
