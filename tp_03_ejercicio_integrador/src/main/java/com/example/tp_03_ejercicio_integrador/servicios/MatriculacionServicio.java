package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.model.Carrera;
import com.example.tp_03_ejercicio_integrador.model.Estudiante;
import com.example.tp_03_ejercicio_integrador.model.EstudianteCarrera;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoCarrera;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoEstudiante;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoEstudianteCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.app.dtos.EstudianteCarreraDTO;


import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("MatriculacionServicio")
public class MatriculacionServicio {

    @Autowired
    private RepoEstudiante repoEstudiante;

    @Autowired
    private RepoCarrera repoCarrera;

    @Autowired
    private RepoEstudianteCarrera repoEstudianteCarrera;

    // b) Matricular un estudiante en una carrera
    @Transactional
    public EstudianteCarrera matricularEstudiante(EstudianteCarreraDTO estudianteCarreraDTO) throws Exception {
        try {
            // Buscar al estudiante por su ID
            Estudiante estudiante = repoEstudiante.findById(estudianteCarreraDTO.getIdEstudiante())
                    .orElseThrow(() -> new Exception("Estudiante no encontrado con ID: " + estudianteCarreraDTO.getIdEstudiante()));

            // Buscar la carrera por su ID
            Carrera carrera = repoCarrera.findById(estudianteCarreraDTO.getIdCarrera())
                    .orElseThrow(() -> new Exception("Carrera no encontrada con ID: " + estudianteCarreraDTO.getIdCarrera()));

            // Crear una nueva inscripción
            EstudianteCarrera inscripcion = new EstudianteCarrera();
            inscripcion.setEstudiante(estudiante);
            inscripcion.setCarrera(carrera);
            inscripcion.setAnioInscripcion(estudianteCarreraDTO.getAnioInscripcion());
            inscripcion.setAnioEgreso(estudianteCarreraDTO.getAnioEgreso() != null ? estudianteCarreraDTO.getAnioEgreso() : null);
            inscripcion.setAntiguedad(estudianteCarreraDTO.getAntiguedad());
            inscripcion.setGraduado(estudianteCarreraDTO.isGraduado());

            // Guardar la inscripción en la base de datos
            EstudianteCarrera savedInscripcion = repoEstudianteCarrera.save(inscripcion);
            return savedInscripcion;
        } catch (Exception e) {
            throw new Exception("Error al matricular estudiante: " + e.getMessage());
        }
    }

    // Obtener todas las inscripciones
    @Transactional
    public List<EstudianteCarrera> findAll() throws Exception {
        try {
            return repoEstudianteCarrera.findAll();
        } catch (Exception e) {
            throw new Exception("Error al obtener inscripciones: " + e.getMessage());
        }
    }

    // Obtener una inscripción por ID
    @Transactional
    public EstudianteCarrera findById(Long id) throws Exception {
        try {
            Optional<EstudianteCarrera> inscripcionOpt = repoEstudianteCarrera.findById(id);
            if (inscripcionOpt.isPresent()) {
                return inscripcionOpt.get();
            } else {
                throw new Exception("Inscripción no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar inscripción: " + e.getMessage());
        }
    }

    // Actualizar una inscripción
    @Transactional
    public EstudianteCarrera update(Long id, EstudianteCarreraDTO estudianteCarreraDTO) throws Exception {
        try {
            // Buscar la inscripción existente por ID
            EstudianteCarrera inscripcion = repoEstudianteCarrera.findById(id)
                    .orElseThrow(() -> new Exception("Inscripción no encontrada con ID: " + id));

            // Actualizar el estudiante si se proporciona un nuevo ID
            if (estudianteCarreraDTO.getIdEstudiante() != null) {
                Estudiante estudiante = repoEstudiante.findById(estudianteCarreraDTO.getIdEstudiante())
                        .orElseThrow(() -> new Exception("Estudiante no encontrado con ID: " + estudianteCarreraDTO.getIdEstudiante()));
                inscripcion.setEstudiante(estudiante);
            }

            // Actualizar la carrera si se proporciona un nuevo ID
            if (estudianteCarreraDTO.getIdCarrera() != null) {
                Carrera carrera = repoCarrera.findById(estudianteCarreraDTO.getIdCarrera())
                        .orElseThrow(() -> new Exception("Carrera no encontrada con ID: " + estudianteCarreraDTO.getIdCarrera()));
                inscripcion.setCarrera(carrera);
            }

            // Actualizar otros campos de la inscripción
            inscripcion.setAnioInscripcion(estudianteCarreraDTO.getAnioInscripcion());
            inscripcion.setAnioEgreso(estudianteCarreraDTO.getAnioEgreso());
            inscripcion.setAntiguedad(estudianteCarreraDTO.getAntiguedad());
            inscripcion.setGraduado(estudianteCarreraDTO.isGraduado());

            // Guardar la inscripción actualizada en la base de datos
            EstudianteCarrera updatedInscripcion = repoEstudianteCarrera.save(inscripcion);
            return updatedInscripcion;
        } catch (Exception e) {
            throw new Exception("Error al actualizar inscripción: " + e.getMessage());
        }
    }

    // Eliminar una inscripción
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            if (repoEstudianteCarrera.existsById(id)) {
                repoEstudianteCarrera.deleteById(id);
                return true;
            } else {
                throw new Exception("Inscripción no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar inscripción: " + e.getMessage());
        }
    }
}

