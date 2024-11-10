package com.example.microservicio_parada.service;

import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.feignClients.MonopatinFeignClient;
import com.example.microservicio_parada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParadaService {

    @Autowired
    ParadaRepository paradaRepository;

    @Autowired
    private MonopatinRepository monopatinRepository;

    @Autowired
    MonopatinFeignClient monopatinFeignClient;

    // Create
    public Parada save(Parada parada) {
        return paradaRepository.save(parada);
    }

    // Read
    public List<Parada> getAll() {
        return paradaRepository.findAll();
    }

    public List<Parada> getAllHabilitadas() {
        return paradaRepository.findAllHabilitadas();
    }

    public List<Parada> getAllDeshabilitadas() {
        return paradaRepository.findAllDeshabilitadas();
    }

    public Parada findById(Long id) {
        return paradaRepository.findById(id).orElse(null);
    }

    // Update
    public Parada update(Parada parada) {
        return paradaRepository.save(parada);
    }*/


    public boolean esParadaValidaParaMonopatin(Long monopatinId, double latitud, double longitud) {

        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatín no encontrado"));

        for (Parada parada : monopatin.getParadasValidas()) {
            if (Math.abs(parada.getLatitud() - latitud) < 0.0001 && Math.abs(parada.getLongitud() - longitud) < 0.0001) {
                return true;
            }
        }
        return false;
    }

    // Delete
    public void delete(Parada parada) {
        paradaRepository.delete(parada);
    }

    // Habilitar Parada
    @Transactional
    public void habilitar(Long id) {
        paradaRepository.habilitar(id);
    }

    // Habilitar Parada
    @Transactional
    public void deshabilitar(Long id) {
        paradaRepository.deshabilitar(id);
    }

    // Read Monopatines
    public List<Monopatin> getMonopatinesById(Long id) {
        List<Monopatin> salida = new ArrayList<Monopatin>();
        List<Long> idMonopatines = paradaRepository.getIdMonopatines(id);

        for (Long idMonopatin : idMonopatines) {
            salida.add(monopatinFeignClient.getMonopatinById(idMonopatin));
        }

        return salida;
    }
}
