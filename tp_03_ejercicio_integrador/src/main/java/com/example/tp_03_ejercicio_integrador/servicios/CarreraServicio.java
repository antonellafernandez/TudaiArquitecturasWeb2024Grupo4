package com.example.tp_03_ejercicio_integrador.servicios;

import com.example.tp_03_ejercicio_integrador.dtos.CarreraConCantInscriptosDTO;
import com.example.tp_03_ejercicio_integrador.dtos.CarreraDTO;
import com.example.tp_03_ejercicio_integrador.dtos.ReporteCarreraDTO;
import com.example.tp_03_ejercicio_integrador.modelos.Carrera;
import com.example.tp_03_ejercicio_integrador.repositorios.RepoCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraServicio {

    @Autowired
    private RepoCarrera repoCarrera;

    // Obtener todas las carreras
    @Transactional
    public List<CarreraDTO> findAll() throws Exception {
        try {
            List<Carrera> carreras = repoCarrera.findAll();
            List<CarreraDTO> carreraDTOS = new ArrayList<>();
            for (Carrera c : carreras) {
                carreraDTOS.add(this.toDTO(c));
            }
            return carreraDTOS;
        } catch (Exception e) {
            throw new Exception("Error al obtener carreras!" + e.getMessage());
        }
    }

    // Obtener una carrera por ID
    @Transactional
    public CarreraDTO findById(int id) throws Exception {
        try {
            Optional<Carrera> carreraBuscada = repoCarrera.findById(id);
            CarreraDTO carreraDTO = this.toDTO(carreraBuscada.get());
            return carreraDTO;
        } catch (Exception e) {
            throw new Exception("Error al obtener carrera con id=" + id + "!" + e.getMessage());
        }
    }

    // Guardar una nueva carrera
    @Transactional
    public CarreraDTO save(Carrera carrera) throws Exception { //Tiene que devolver un DTO? Al igual que update
        try {
            repoCarrera.save(carrera);
            CarreraDTO carreraDTO = this.toDTO(carrera);
            return carreraDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar carrera!" + e.getMessage());
        }
    }

    // Actualizar una carrera
    @Transactional
    public CarreraDTO update(int id, Carrera carrera) throws Exception {
        try {
            if (repoCarrera.existsById(id)){
                repoCarrera.save(carrera);
                CarreraDTO carreraDTO = this.toDTO(carrera);
                return carreraDTO;
            }
            throw new Exception("No existe un carrera con id=" + id + "!");
        } catch (Exception e) {
            throw new Exception("Error al actualizar carrera con id=" + id + "!" + e.getMessage());
        }
    }

    // Eliminar una carrera
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
        return new CarreraDTO(carrera.getNombre());
    }

    @Transactional
    public List<ReporteCarreraDTO> generarReporteCarreras(){
        return repoCarrera.getReporteCarreras();
    }

    @Transactional
    public List<CarreraConCantInscriptosDTO> getCarrerasOrdenadasPorInscriptos(){
        return repoCarrera.getCarrerasOrdenadasPorInscriptos();
    }
}
