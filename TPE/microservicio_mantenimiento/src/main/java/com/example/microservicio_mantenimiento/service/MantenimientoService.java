package com.example.microservicio_mantenimiento.service;


import com.example.microservicio_mantenimiento.models.Monopatin;
import com.example.microservicio_mantenimiento.repository.MantenimientoRepository;
import com.example.microservicio_mantenimiento.feignClients.MonopatinFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    // Read Monopatines
    public List<Monopatin> getMonopatinesById(Long id) {
        List<Monopatin> enMantenimiento = new ArrayList<Monopatin>();
        List<Long> idMonopatines = mantenimientoRepository.getIdMonopatines(id);

        for (Long idMonopatin : idMonopatines) {
            Monopatin monopatin = monopatinFeignClient.getMonopatinById(idMonopatin);

            if (monopatin.getKmRecorridosTotales() >= umbralKm || monopatin.getTiempoRecorridosTotales() >= umbralTiempo) {
                enMantenimiento.add(monopatin);
            }
        }

        return enMantenimiento;
    }
}
