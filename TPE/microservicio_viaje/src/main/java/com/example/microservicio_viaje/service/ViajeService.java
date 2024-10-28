package com.example.microservicioviaje.service;

import com.example.microservicioviaje.entity.Viaje;
import com.example.microservicioviaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    public Viaje save(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        viajeRepository.deleteById(id);
    }

    public Viaje update(Viaje viaje) {
        return viajeRepository.save(viaje);
    }
    public void pausarViaje(Long id, LocalDateTime pausa) {
        Viaje viaje = findById(id);
        if (viaje != null) {
            viaje.getInicioPausasFinal().add(pausa);
            save(viaje);
        }
    }

}
