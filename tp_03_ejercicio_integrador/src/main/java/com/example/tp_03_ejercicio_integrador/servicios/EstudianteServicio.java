package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.model.Estudiante;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class EstudianteServicio {

    @Autowired
    private RepoEstudiante repoEstudiante;

    // Guardar un nuevo estudiante
    @Transactional
    public Estudiante guardarEstudiante(Estudiante estudiante) throws Exception {
        try {
            return repoEstudiante.save(estudiante);
        } catch (Exception e) {
            throw new Exception("Error al guardar estudiante: " + e.getMessage());
        }
    }

    // Obtener todos los estudiantes
    @Transactional
    public List<Estudiante> findAll() throws Exception {
        try {
            return repoEstudiante.findAll();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes: " + e.getMessage());
        }
    }

    // Obtener un estudiante por ID
    @Transactional
    public Estudiante findById(Long id) throws Exception {
        try {
            return repoEstudiante.findById(id)
                    .orElseThrow(() -> new Exception("Estudiante no encontrado con ID: " + id));
        } catch (Exception e) {
            throw new Exception("Error al buscar estudiante: " + e.getMessage());
        }
    }

    // Actualizar un estudiante
    @Transactional
    public Estudiante update(Long id, Estudiante estudiante) throws Exception {
        try {
            Estudiante estudianteExistente = findById(id);
            estudianteExistente.setNombre(estudiante.getNombre());
            estudianteExistente.setApellido(estudiante.getApellido());
            return repoEstudiante.save(estudianteExistente);
        } catch (Exception e) {
            throw new Exception("Error al actualizar estudiante: " + e.getMessage());
        }
    }

    // Eliminar un estudiante
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            if (repoEstudiante.existsById(id)) {
                repoEstudiante.deleteById(id);
                return true;
            } else {
                throw new Exception("Estudiante no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar estudiante: " + e.getMessage());
        }
    }

    public EstudianteDTO altaEstudiante(EstudianteDTO estudianteDTO) {

    }
}
