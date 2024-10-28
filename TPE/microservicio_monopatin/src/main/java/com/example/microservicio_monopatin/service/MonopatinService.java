package com.example.microservicio_monopatin.service;

import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;

    @Autowired
    private ParadaRepository paradaRepository;

    // Pausar el monopatín en una parada válida
    public boolean pausarMonopatin(Long id, Long paradaId) {
        Monopatin monopatin = monopatinRepository.findById(id).orElse(null);
        if (monopatin != null && paradaId != null && monopatin.isDisponible()) {
            Parada parada = paradaRepository.findById(paradaId).orElse(null);
            if (parada != null && esParadaPermitida(monopatin, parada)) {
                monopatin.setDisponible(false);
                parada.agregarMonopatin(monopatin);
                monopatinRepository.save(monopatin);
                return true;
            }
        }
        return false;
    }

    // Reanudar el monopatín, quitándolo de la parada actual
    public boolean reanudarMonopatin(Long id) {
        Monopatin monopatin = monopatinRepository.findById(id).orElse(null);
        if (monopatin != null && !monopatin.isDisponible() && monopatin.getParada() != null) {
            if (esParadaPermitida(monopatin, monopatin.getParada())) {
                Parada paradaActual = monopatin.getParada();
                paradaActual.quitarMonopatin(monopatin);
                monopatin.setDisponible(true);
                monopatin.setParada(null); // Limpia la parada para indicar que está en movimiento
                monopatinRepository.save(monopatin);
                return true;
            }
        }
        return false;
    }

    private boolean esParadaPermitida(Monopatin monopatin, Parada parada) {
        return monopatin.getGps() != null && monopatin.getGps().validarParadaPermitida(parada);
    }

    // Método para registrar una parada en el GPS del monopatín
    public void registrarParadaPermitida(Long idGps, Parada parada) {
        Gps gps = gpsRepository.findById(idGps).orElse(null);
        if (gps != null) {
            gps.registrarParada(parada);
            gpsRepository.save(gps);
        }
    }

    // Método para quitar una parada permitida en el GPS del monopatín
    public void quitarParadaPermitida(Long idGps, Parada parada) {
        Gps gps = gpsRepository.findById(idGps).orElse(null);
        if (gps != null) {
            gps.quitarParada(parada);
            gpsRepository.save(gps);
        }
    }
}

