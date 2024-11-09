package com.example.microservicio_mantenimiento.service;


import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MantenimientoService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Value("${mantenimiento.umbral.km}")
    private Long umbralKm;

    @Value("${mantenimiento.umbral.tiempo}")
    private Long umbralTiempo;

    public Long calcularKmTotales(Long monopatinId) {
        List<Viaje> viajes = viajeRepository.findByMonopatinId(monopatinId);
        return viajes.stream().mapToLong(Viaje::getKmRecorridos).sum();
    }

    public Long calcularTiempoUsoTotal(Long monopatinId) {
        List<Viaje> viajes = viajeRepository.findByMonopatinId(monopatinId);
        return viajes.stream()
                .mapToLong(viaje -> {
                    LocalDateTime inicio = viaje.getFechaHoraInicio();
                    LocalDateTime fin = viaje.getFechaHoraFin();
                    return Duration.between(inicio, fin).toMinutes();
                })
                .sum();
    }

    public boolean necesitaMantenimiento(Long monopatinId) {
        Long kmTotales = calcularKmTotales(monopatinId);
        Long tiempoUsoTotal = calcularTiempoUsoTotal(monopatinId);

        boolean mantenimientoPorKm = kmTotales >= umbralKm;
        boolean mantenimientoPorTiempo = tiempoUsoTotal >= umbralTiempo;

        return mantenimientoPorKm || mantenimientoPorTiempo;
    }
}