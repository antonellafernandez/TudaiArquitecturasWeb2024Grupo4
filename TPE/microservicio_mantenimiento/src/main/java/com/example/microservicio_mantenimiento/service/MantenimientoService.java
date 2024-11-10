package com.example.microservicio_mantenimiento.service;


import com.example.microservicio_mantenimiento.models.Monopatin;
import com.example.microservicio_mantenimiento.repository.MantenimientoRepository;
import com.example.microservicio_mantenimiento.feignClients.MonopatinFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    // Read Monopatin
    public List<Monopatin> registrarMonopatinEnMantenimiento(Long id) {
        Long idMonopatin = mantenimientoRepository.getIdMonopatin(id);
        Monopatin monopatin = monopatinFeignClient.getMonopatinById(idMonopatin);

        if (monopatin.getKmRecorridosTotales() >= umbralKm || monopatin.getTiempoRecorridosTotales() >= umbralTiempo) {
            // ???
        }

        return monopatin;
    }
}
