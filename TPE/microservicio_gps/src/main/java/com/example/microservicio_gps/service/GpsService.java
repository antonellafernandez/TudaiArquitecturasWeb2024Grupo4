package com.example.microservicio_gps.service;


import com.example.microservicio_gps.entity.Gps;
import com.example.microservicio_gps.repository.GpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GpsService {

    @Autowired
    GpsRepository GpsRepository;

    public List<Gps> getAll() {
        return GpsRepository.findAll();
    }

    public Gps save(Gps gps) {
        return GpsRepository.save(gps);
    }

    public void delete(Gps gps) {
        GpsRepository.delete(gps);
    }

    public Gps findById(Long id) {
        return GpsRepository.findById(id).orElse(null);
    }

    public Gps update(Gps gps) {
        return GpsRepository.save(gps);
    }
}