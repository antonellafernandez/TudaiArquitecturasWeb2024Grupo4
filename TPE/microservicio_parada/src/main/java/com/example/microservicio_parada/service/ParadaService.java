package com.example.microservicio_parada.service;

import com.example.microservicio_parada.entity.Monopatin;
import com.example.microservicio_parada.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    public List<Parada> getAllParadas() {
        return paradaRepository.findAll();
    }

    public Optional<Parada> getParadaById(Long id) {
        return paradaRepository.findById(id);
    }

    public Parada createParada(Parada parada) {
        return paradaRepository.save(parada);
    }

    public Parada updateParada(Long id, Parada parada) {
        return paradaRepository.findById(id)
                .map(existingParada -> {
                    parada.setIdParada(existingParada.getIdParada());
                    return paradaRepository.save(parada);
                })
                .orElseThrow(() -> new EntityNotFoundException("Parada no encontrada"));
    }

    public void deleteParada(Long id) {
        paradaRepository.deleteById(id);
    }
}
