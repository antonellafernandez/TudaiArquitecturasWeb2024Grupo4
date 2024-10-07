package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.dtos.CarreraConCantInscriptosDTO;
import com.example.tp_03_ejercicio_integrador.dtos.CarreraDTO;
import com.example.tp_03_ejercicio_integrador.dtos.ReporteCarreraDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.modelos.EstudianteCarrera;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraServicio implements BaseService<Carrera> {

    @Autowired
    private RepoCarrera repoCarrera;

    // Obtener todas las carreras
    @Override
    @Transactional
    public List<Carrera> findAll() throws Exception {
        try {
            return repoCarrera.findAll();
        } catch (Exception e) {
            throw new Exception("Error al obtener carreras!" + e.getMessage());
        }
    }

    // Obtener una carrera por ID
    @Override
    @Transactional
    public Carrera findById(int id) throws Exception {
        try {
            Optional<Carrera> carreraBuscada = repoCarrera.findById(id);
            return carreraBuscada.get();
        } catch (Exception e) {
            throw new Exception("Error al obtener carrera con id=" + id + "!" + e.getMessage());
        }
    }

    // Guardar una nueva carrera
    @Override
    @Transactional
    public Carrera save(Carrera carrera) throws Exception {
        try {
            return repoCarrera.save(carrera);
        } catch (Exception e) {
            throw new Exception("Error al guardar carrera!" + e.getMessage());
        }
    }

    // Actualizar una carrera
    @Override
    @Transactional
    public Carrera update(int id, Carrera carrera) throws Exception {
        try {
            Carrera carreraExistente = findById(id);
            carreraExistente.setNombre(carrera.getNombre());

            return repoCarrera.save(carreraExistente);
        } catch (Exception e) {
            throw new Exception("Error al actualizar carrera con id=" + id + "!" + e.getMessage());
        }
    }

    // Eliminar una carrera
    @Override
    @Transactional
    public boolean delete(int id) throws Exception {
        try {
            if (repoCarrera.existsById(id)) {
                repoCarrera.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar carrera con id=" + id + "!" + e.getMessage());
        }
    }

    // Obtener CarreraDTO
    public CarreraDTO toDTO(Carrera carrera) {
        CarreraDTO carreraDTO = new CarreraDTO();

        for (EstudianteCarrera inscripcion : carrera.getInscripciones()) {
            carreraDTO.addInscripcion(inscripcion);
        }

        return carreraDTO;
    }

    public List<ReporteCarreraDTO> generarReporteCarreras(){
        return repoCarrera.getReporteCarreras();
    }

    public List<CarreraConCantInscriptosDTO> getCarrerasConCantInscriptos(){
        return repoCarrera.getCarrerasConEstudiantesOrdenadas();
    }
}
