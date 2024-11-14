package com.example.microservicio_parada.service;

import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.feignClients.MonopatinFeignClient;
import com.example.microservicio_parada.models.Monopatin;
import com.example.microservicio_parada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParadaService {

    @Autowired
    ParadaRepository paradaRepository;

    @Autowired
    MonopatinFeignClient monopatinFeignClient;

    // Create
    @Transactional
    public Parada save(Parada parada) {
        return paradaRepository.save(parada);
    }

    // Read
    @Transactional(readOnly = true)
    public List<Parada> getAll() {
        return paradaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Parada> getAllHabilitadas() {
        return paradaRepository.findAllHabilitadas();
    }

    @Transactional(readOnly = true)
    public List<Parada> getAllDeshabilitadas() {
        return paradaRepository.findAllDeshabilitadas();
    }

    @Transactional(readOnly = true)
    public Parada findById(Long id) {
        return paradaRepository.findById(id).orElse(null);
    }

    // Update
    @Transactional
    public Parada update(Parada parada) {
        return paradaRepository.save(parada);
    }

    // Delete
    @Transactional
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
    @Transactional(readOnly = true)
    public List<Monopatin> getMonopatinesById(Long id) {
        List<Monopatin> salida = new ArrayList<Monopatin>();
        List<Long> idMonopatines = paradaRepository.getIdMonopatines(id);

        for (Long idMonopatin : idMonopatines) {
            salida.add(monopatinFeignClient.getMonopatinById(idMonopatin));
        }

        return salida;
    }

    // Obtener monopatines cercanos.
    @Transactional(readOnly = true)
    public List<Monopatin> getMonopatinesCercanos(double latitud, double longitud, double radio) {
        List<Parada> paradas = paradaRepository.findAllHabilitadas();
        List<Parada> paradasCercanas = paradas.stream()
                .filter(parada -> calcularDistancia(latitud, longitud, parada.getLatitud(), parada.getLongitud()) <= radio)
                .collect(Collectors.toList());

        List<Monopatin> monopatinesCercanos = new ArrayList<>();
        for (Parada parada : paradasCercanas) {
            List<Long> idMonopatines = parada.getIdMonopatines();
            for (Long idMonopatin : idMonopatines) {
                monopatinesCercanos.add(monopatinFeignClient.getMonopatinById(idMonopatin));
            }
        }
        return monopatinesCercanos;
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA * c;
    }

    @Transactional
    public Parada quitarMonopatin(Long idParada, Long idMonopatin) {
        try{
            Parada parada = findById(idParada);
            parada.quitarMonopatin(idMonopatin);
            paradaRepository.save(parada);
            return parada;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
