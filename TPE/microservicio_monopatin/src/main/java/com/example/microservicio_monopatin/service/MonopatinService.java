package com.example.microservicio_monopatin.service;

import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MonopatinService {

    @Autowired
    MonopatinRepository monopatinRepository;

    public List<Monopatin> getAll() {
        return monopatinRepository.findAll();
    }

    public Monopatin save(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    public void delete(Monopatin monopatin) {
        monopatinRepository.delete(monopatin);
    }

    public Monopatin findById(Long id) {
        return monopatinRepository.findById(id).orElse(null);
    }

    public Monopatin update(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }
}



