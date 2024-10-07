package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.dtos.EstudianteDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Estudiante;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class EstudianteServicio implements BaseService<Estudiante> {

    @Autowired
    private RepoEstudiante repoEstudiante;

    // Obtener todos los estudiantes
    @Override
    @Transactional
    public List<Estudiante> findAll() throws Exception {
        try {
            return repoEstudiante.findAll();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes: " + e.getMessage());
        }
    }

    // Obtener un estudiante por ID
    @Override
    @Transactional
    public Estudiante findById(int id) throws Exception {
        try {
            return repoEstudiante.findById(id)
                    .orElseThrow(() -> new Exception("Estudiante no encontrado con ID: " + id));
        } catch (Exception e) {
            throw new Exception("Error al buscar estudiante: " + e.getMessage());
        }
    }

    // Guardar un nuevo estudiante
    @Override
    @Transactional
    public Estudiante save(Estudiante estudiante) throws Exception {
        try {
            return repoEstudiante.save(estudiante);
        } catch (Exception e) {
            throw new Exception("Error al guardar estudiante: " + e.getMessage());
        }
    }

    // Actualizar un estudiante
    @Override
    @Transactional
    public Estudiante update(int id, Estudiante estudiante) throws Exception {
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
    @Override
    @Transactional
    public boolean delete(int id) throws Exception {
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
}
