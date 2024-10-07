package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.dtos.EstudianteCarreraDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.modelos.Estudiante;
import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
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
public class EstudianteCarreraServicio implements BaseService<EstudianteCarrera> {

    @Autowired
    private RepoEstudiante repoEstudiante;

    @Autowired
    private RepoCarrera repoCarrera;

    @Autowired
    private RepoEstudianteCarrera repoEstudianteCarrera;

    // Obtener todas las inscripciones
    @Override
    @Transactional
    public List<EstudianteCarrera> findAll() throws Exception {
        try {
            return repoEstudianteCarrera.findAll();
        } catch (Exception e) {
            throw new Exception("Error al obtener inscripciones!" + e.getMessage());
        }
    }

    // Obtener una inscripción por ID
    @Override
    @Transactional
    public EstudianteCarrera findById(int id) throws Exception {
        try {
            Optional<EstudianteCarrera> inscripcionBuscada = repoEstudianteCarrera.findById(id);
            return inscripcionBuscada.get();
        } catch (Exception e) {
            throw new Exception("Error al buscar inscripción  con id=" + id + "!" + e.getMessage());
        }
    }

    // 2b) Matricular un estudiante en una carrera.
    @Override
    @Transactional
    public EstudianteCarrera save(EstudianteCarrera estudianteCarrera) throws Exception {
        try {
            return repoEstudianteCarrera.save(estudianteCarrera);
        } catch (Exception e) {
            throw new Exception("Error al matricular estudiante!" + e.getMessage());
        }
    }

    // Actualizar una inscripción
    @Override
    @Transactional
    public EstudianteCarrera update(int id, EstudianteCarrera estudianteCarrera) throws Exception {
        try {
            // Buscar la inscripción existente por ID
            EstudianteCarrera inscripcion = repoEstudianteCarrera.findById(id)
                    .orElseThrow(() -> new Exception("Inscripción no encontrada con id=" + id + "!"));

            // Buscar estudiante existente
            int idEstudiante = estudianteCarrera.getEstudiante().getDni();
            Estudiante estudiante = repoEstudiante.findById(idEstudiante)
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id=" + idEstudiante + "!"));

            // Buscar carrera existente
            int idCarrera = estudianteCarrera.getCarrera().getId();
            Carrera carrera = repoCarrera.findById(idCarrera)
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id=" + idCarrera + "!"));

            // Actualizar campos de inscripcion
            inscripcion.setEstudiante(estudiante);
            inscripcion.setCarrera(carrera);
            inscripcion.setAnioInscripcion(estudianteCarrera.getAnioInscripcion());
            inscripcion.setAnioEgreso(estudianteCarrera.getAnioEgreso());
            inscripcion.setAntiguedad(estudianteCarrera.getAntiguedad());
            inscripcion.setGraduado(estudianteCarrera.isGraduado());

            // Guardar la inscripción actualizada en la base de datos
            return repoEstudianteCarrera.save(inscripcion);
        } catch (Exception e) {
            throw new Exception("Error al actualizar inscripción con id=" + id + "!" + e.getMessage());
        }
    }

    // Eliminar una inscripción
    @Override
    @Transactional
    public boolean delete(int id) throws Exception {
        try {
            if (repoEstudianteCarrera.existsById(id)) {
                repoEstudianteCarrera.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar inscripción  con id=" + id + "!" + e.getMessage());
        }
    }

    // Obtener EstudianteCarreraDTO
    public EstudianteCarreraDTO toDTO(EstudianteCarrera estudianteCarrera) {
        EstudianteCarreraDTO estudianteCarreraDTO = new EstudianteCarreraDTO(
                estudianteCarrera.getEstudiante().getLu(),
                estudianteCarrera.getCarrera().getNombre(),
                estudianteCarrera.getAnioInscripcion(),
                estudianteCarrera.getAnioEgreso(),
                estudianteCarrera.getAntiguedad(),
                estudianteCarrera.isGraduado()
        );

        return estudianteCarreraDTO;
    }
}
