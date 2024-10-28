package com.example.microservicio_viaje.service;

import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ViajeService {

    @Autowired
    ViajeRepository viajeRepository;

    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    public Viaje save(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    public void delete(Viaje viaje) {
        viajeRepository.delete(viaje);
    }

    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    public Viaje update(Viaje viaje) {
        return viajeRepository.save(viaje);
    }
}