package com.example.microservicio_mantenimiento.service;


import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import com.example.microservicio_mantenimiento.models.Monopatin;
import com.example.microservicio_mantenimiento.repository.MantenimientoRepository;
import com.example.microservicio_mantenimiento.feignClients.MonopatinFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    // Read Monopatin
    @Transactional
    public Mantenimiento registrarMonopatinEnMantenimiento(Long idMonopatin, Long umbralKm, Long umbralTiempo) {
        Monopatin monopatin = monopatinFeignClient.getMonopatinById(idMonopatin);
        Mantenimiento newMantenimiento = new Mantenimiento(monopatin.getGpsId(), LocalDateTime.now(), null);
        if (monopatin.getDisponible() && monopatin.getKmRecorridosTotales() >= umbralKm ||
                monopatin.getTiempoRecorridosTotales() >= umbralTiempo) {
            mantenimientoRepository.save(newMantenimiento);
            monopatinFeignClient.deshabilitar(idMonopatin);
            return newMantenimiento;
        }
        throw new IllegalArgumentException("El monopatin no está apto para mantenimiento");
    }

    @Transactional
    public Mantenimiento finalizarMantenimiento(Long idMantenimiento) {

        if (mantenimientoRepository.findById(idMantenimiento) != null){
            if(mantenimientoRepository.monopatinEnMantenimiento(idMantenimiento))
                throw new IllegalArgumentException("El mantenimiento estaba finalizado");
            else
                return mantenimientoRepository.finalizarMantenimiento(idMantenimiento, LocalDateTime.now());
        }

        throw new IllegalArgumentException("El mantenimiento no existe");
    }
}
