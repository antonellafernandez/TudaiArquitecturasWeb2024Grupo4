package com.example.microservicio_mantenimiento.service;


import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import com.example.microservicio_mantenimiento.models.Monopatin;
import com.example.microservicio_mantenimiento.repository.MantenimientoRepository;
import com.example.microservicio_mantenimiento.feignClients.MonopatinFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    // Read Monopatin
    public Mantenimiento registrarMonopatinEnMantenimiento(Long idMonopatin, Long umbralKm, Long umbralTiempo) {
        Monopatin monopatin = monopatinFeignClient.getMonopatinById(idMonopatin);
        Mantenimiento newMantenimiento = new Mantenimiento(monopatin.getGpsId(), LocalDateTime.now(), null);
        if (monopatin.getDisponible() && monopatin.getKmRecorridosTotales() >= umbralKm ||
                monopatin.getTiempoRecorridosTotales() >= umbralTiempo) {
            mantenimientoRepository.save(newMantenimiento);
            monopatinFeignClient.deshabilitar();
            return newMantenimiento;
        }
        throw new IllegalArgumentException("El monopatin no está apto para mantenimiento");
    }

    public Mantenimiento finalizarMantenimiento(Long idMonopatin) {

        if (mantenimientoRepository.findByIdMonopatin(idMonopatin) != null)
            return mantenimientoRepository.findById(idMonopatin).get();
    }
}
