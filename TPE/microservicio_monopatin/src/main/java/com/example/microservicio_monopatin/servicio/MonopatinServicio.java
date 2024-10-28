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


public boolean pausarMonopatin(Long id, Long paradaId) {
    Monopatin monopatin = monopatinRepository.findById(id).orElse(null);
    if (monopatin != null && paradaId != null && monopatin.isDisponible()) {
        Parada parada = paradaRepository.findById(paradaId).orElse(null);
        if (parada != null && esParadaPermitida(monopatin, parada)) {
            monopatin.setDisponible(false);
            monopatin.setParada(parada);
            monopatinRepository.save(monopatin);
            return true;
        }
    }
    return false;
}

public boolean reanudarMonopatin(Long id) {
    Monopatin monopatin = monopatinRepository.findById(id).orElse(null);
    if (monopatin != null && !monopatin.isDisponible() && monopatin.getParada() != null) {
        if (esParadaPermitida(monopatin, monopatin.getParada())) {
            monopatin.setDisponible(true);
            monopatin.setParada(null); // Limpia la parada indicando que está en movimiento
            monopatinRepository.save(monopatin);
            return true;
        }
    }
    return false;
}

private boolean esParadaPermitida(Monopatin monopatin, Parada parada) {
    return monopatin.getGps() != null && monopatin.getGps().validarParadaPermitida(parada);
}
}
