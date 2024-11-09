package com.example.microservicio_parada.service;

import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.feignClients.MonopatinFeignClient;
import com.example.microservicio_parada.models.Monopatin;
import com.example.microservicio_parada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParadaService {

    @Autowired
    ParadaRepository paradaRepository;

    @Autowired
    MonopatinFeignClient monopatinFeignClient;

    public List<Parada> getAll() {
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
    }

    public List<Monopatin> getMonopatinesById(Long id) {
        List<Monopatin> salida = new ArrayList<Monopatin>();
        List<Long> id_monopatines = paradaRepository.getIdMonopatines(id);

        for (Long id_monopatin : id_monopatines) {
            salida.add(monopatinFeignClient.getMonopatinById(id_monopatin));
        }

       return salida;
    }
}
