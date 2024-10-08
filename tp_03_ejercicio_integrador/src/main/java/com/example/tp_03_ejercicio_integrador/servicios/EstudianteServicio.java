package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.dtos.EstudianteDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Estudiante;
import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
            throw new Exception("Error al obtener estudiantes!" + e.getMessage());
        }
    }

    // Obtener un estudiante por ID
    @Override
    @Transactional
    public Estudiante findById(int id) throws Exception {
        try {
            Optional<Estudiante> estudianteBuscado = repoEstudiante.findById(id);
            return estudianteBuscado.get();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiante con id=" + id + "!" + e.getMessage());
        }
    }

    // 2a) Dar de alta un estudiante.
    // Guardar un nuevo estudiante
    @Override
    @Transactional
    public Estudiante save(Estudiante estudiante) throws Exception {
        try {
            return repoEstudiante.save(estudiante);
        } catch (Exception e) {
            throw new Exception("Error al guardar estudiante!" + e.getMessage());
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
            throw new Exception("Error al actualizar estudiante con id=" + id + "!" + e.getMessage());
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
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar estudiante con id=" + id + "!" + e.getMessage());
        }
    }

    // Obtener EstudianteDTO
    public static EstudianteDTO toDTO(Estudiante estudiante) {
        EstudianteDTO estudianteDTO = new EstudianteDTO(
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getEdad(),
                estudiante.getGenero(),
                estudiante.getCiudadResidencia(),
                estudiante.getLu()
        );

        return estudianteDTO;
    }

    public Estudiante obtenerPorLu(Long lu) throws Exception {
        try{
            return repoEstudiante.getEstudianteByLu(lu);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
