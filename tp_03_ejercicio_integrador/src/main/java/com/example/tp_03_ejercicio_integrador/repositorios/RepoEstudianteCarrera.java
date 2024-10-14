package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("RepoEstudianteCarrera")
public interface RepoEstudianteCarrera extends JpaRepository<EstudianteCarrera, Integer> {

}
