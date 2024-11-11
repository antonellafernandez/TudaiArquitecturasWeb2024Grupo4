package com.example.microservicio_viaje.service;

import com.example.microservicio_viaje.dto.ReporteMonopatinesPorViajesYAnio;
import com.example.microservicio_viaje.dto.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.microservicio_viaje.dto.ReporteUsoPorTiempoDto;
import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Transactional(readOnly = true)
    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    @Transactional
    public Viaje save(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    @Transactional
    public void delete(Viaje viaje) {
        viajeRepository.delete(viaje);
    }

    @Transactional(readOnly = true)
    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Viaje update(Viaje viaje) {
        return viajeRepository.save(viaje);
    }


    @Transactional
    public void registrarInicioPausa(Long idViaje, LocalDateTime fechaHoraInicio) {
        // Lógica para registrar el inicio de una pausa
        Viaje viaje = viajeRepository.findById(idViaje).get();
        if (viaje != null) {
            viaje.getInicioPausasFinal().add(fechaHoraInicio);
            viajeRepository.save(viaje);
        }
    }

    @Transactional(readOnly = true)
    public LocalDateTime obtenerInicioUltimaPausa(Long monopatinId) {
        // Obtener el inicio de la última pausa
        Viaje viaje = viajeRepository.findById(monopatinId).get();
        if (viaje != null && !viaje.getInicioPausasFinal().isEmpty()) {
            return viaje.getInicioPausasFinal().get(viaje.getInicioPausasFinal().size() - 1);
        }
        return null;
    }

    @Transactional
    public void registrarFinPausa(Long monopatinId, LocalDateTime fechaHoraFin) {
        // Lógica para registrar el fin de una pausa
        Viaje viaje = viajeRepository.findById(monopatinId).get();
        if (viaje != null && !viaje.getInicioPausasFinal().isEmpty()) {
            viaje.getInicioPausasFinal().add(fechaHoraFin);
            viajeRepository.save(viaje);
        }
    }

    @Transactional
    public void finalizarViaje(Long viajeId, LocalDateTime fechaHoraFin, Long kmRecorridos) {
        // Lógica para finalizar el viaje
        Viaje viaje = viajeRepository.findById(viajeId).orElse(null);
        if (viaje != null) {
            viaje.setFechaHoraFin(fechaHoraFin);
            viaje.setKmRecorridos(kmRecorridos);
            viajeRepository.save(viaje);
        }
    }

    @Transactional
    public void iniciarViaje(Long monopatinId, LocalDateTime fechaHoraInicio) {
        // Lógica para iniciar un viaje
        Viaje viaje = new Viaje();
        viaje.setFechaHoraInicio(fechaHoraInicio);
        viaje.setFechaHoraFin(null);
        viaje.setKmRecorridos(0L);
        viaje.setInicioPausasFinal(new ArrayList<>());
        viajeRepository.save(viaje);
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> getDuracionPausas() {
        List<ReporteUsoPorTiempoDto> pausaMonopatines = viajeRepository.reporteUsoPorTiempo();
        return pausaMonopatines.stream()
                .collect(Collectors.groupingBy(
                        ReporteUsoPorTiempoDto::getIdMonopatin,
                        Collectors.summingLong(reporte -> this.sumarPausas(reporte.getPausas()))  // Sumar los minutos de las pausas
                ));
    }

    private Long sumarPausas(List<LocalDateTime> pausas) {
        Long duracion = 0L;
        if (pausas != null && !pausas.isEmpty())
            for (int i = 0; i < pausas.size() - 1; i += 2) {
                duracion += Duration.between(pausas.get(i), pausas.get(i + 1)).toMinutes();
            }
        return duracion;
    }

    @Transactional(readOnly = true)
    public List<ReporteMonopatinesPorViajesYAnio>getReportePorViajeYAnio(Long cantViajes, Long anio){
        return viajeRepository.getReportePorViajeYAnio(cantViajes, anio);
    }

    @Transactional(readOnly = true)
    public ReporteTotalFacturadoEntreMesesDeAnio getReporteTotalFacturadoEntreMesesDeAnio(Long mesInicio, Long mesFin, Long anio){
        return viajeRepository.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio);
    }
}
