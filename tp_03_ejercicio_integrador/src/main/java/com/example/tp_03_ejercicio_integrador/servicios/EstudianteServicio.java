package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.dtos.EstudianteDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Estudiante;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServicio {

    @Autowired
    private RepoEstudiante repoEstudiante;

    // Obtener todos los estudiantes
    @Transactional
    public List<EstudianteDTO> findAll() throws Exception {
        try {
            List<Estudiante> estudiantes = repoEstudiante.findAll();
            List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
            for (Estudiante e : estudiantes) {
                estudianteDTOS.add(this.toDTO(e));
            }
            return estudianteDTOS;
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes!" + e.getMessage());
        }
    }

    // Obtener un estudiante por ID
    @Transactional
    public EstudianteDTO findById(int id) throws Exception {
        try {
            Optional<Estudiante> estudianteBuscado = repoEstudiante.findById(id);
            EstudianteDTO estudianteDTO = this.toDTO(estudianteBuscado.get());
            return estudianteDTO;
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiante con id=" + id + "!" + e.getMessage());
        }
    }

    // 2a) Dar de alta un estudiante.
    // Guardar un nuevo estudiante
    @Transactional
    public EstudianteDTO save(Estudiante estudiante) throws Exception {
        try {
            Estudiante estudianteGuardado = repoEstudiante.save(estudiante);
            EstudianteDTO estudianteDTO = this.toDTO(estudianteGuardado);
            return estudianteDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar estudiante!" + e.getMessage());
        }
    }

    // Actualizar un estudiante
    @Transactional
    public EstudianteDTO update(int id, Estudiante estudiante) throws Exception {
        try {
            if (repoEstudiante.existsById(id)){
                Estudiante estudianteGuardado = repoEstudiante.save(estudiante);
                EstudianteDTO estudianteDTO = this.toDTO(estudianteGuardado);
                return estudianteDTO;
            }
            throw new Exception("Estudiante no encontrado");
        } catch (Exception e) {
            throw new Exception("Error al actualizar estudiante con id=" + id + "!" + e.getMessage());
        }
    }

    // Eliminar un estudiante
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
    public EstudianteDTO toDTO(Estudiante estudiante) {
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

    public EstudianteDTO obtenerPorLu(Long lu) throws Exception {
        try{
            Estudiante estudiante = repoEstudiante.getEstudianteByLu(lu);
            EstudianteDTO estudianteDTO = this.toDTO(estudiante);
            return estudianteDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EstudianteDTO> obtenerPorGenero(String genero){
        try{
            List<Estudiante> estudiantes = repoEstudiante.obtenerPorGenero(genero);
            List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
            for (Estudiante estudiante : estudiantes) {
                estudianteDTOS.add(this.toDTO(estudiante));
            }
            return estudianteDTOS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EstudianteDTO> getEstudiantesByCarreraAndCiudad(String carrera, String ciudad){
        try{
            List<Estudiante> estudiantes = repoEstudiante.getEstudiantesByCarreraAndCiudad(carrera, ciudad);

            List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
            for (Estudiante estudiante : estudiantes) {
                estudianteDTOS.add(this.toDTO(estudiante));
            }
            return estudianteDTOS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
