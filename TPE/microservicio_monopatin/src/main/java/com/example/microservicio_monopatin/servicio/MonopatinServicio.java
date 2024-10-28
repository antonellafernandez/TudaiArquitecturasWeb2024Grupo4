package com.example.microserviciomonopatines.service;

import com.example.microserviciomonopatines.entity.Monopatin;
import com.example.microserviciomonopatines.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;

    public List<Monopatin> getAll() {
        return monopatinRepository.findAll();
    }

    public Monopatin findById(Long id) {
        return monopatinRepository.findById(id).orElse(null);
    }

    public Monopatin save(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    //public Monopatin update(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    public void pausarMonopatin(Long id) {
        Monopatin monopatin = findById(id);
        if (monopatin != null) {
            monopatin.setDisponible(false);
            save(monopatin);
        }
    }

    public void reanudarMonopatin(Long id) {
        Monopatin monopatin = findById(id);
        if (monopatin != null) {
            monopatin.setDisponible(true);
            save(monopatin);
        }

        public boolean esParadaPermitida(Long idMonopatin, Parada parada) {
            Monopatin monopatin = findById(idMonopatin);
            if (monopatin != null && monopatin.getGps() != null) {
                return monopatin.getGps().validarParadaPermitida(parada);
            }
            return false;
        }
}
