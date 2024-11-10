package com.example.microservicio_parada.service;

import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParadaService {

    @Autowired
    ParadaRepository paradaRepository;
    @Autowired
    private MonopatinRepository monopatinRepository;
   /* public List<Parada> getAll() {
        return paradaRepository.findAll();
    }

    public Parada save(Parada parada) {
        return paradaRepository.save(parada);
    }

    public void delete(Parada parada) {
        paradaRepository.delete(parada);
    }

    public Parada findById(Long id) {
        return paradaRepository.findById(id).orElse(null);
    }

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


}
