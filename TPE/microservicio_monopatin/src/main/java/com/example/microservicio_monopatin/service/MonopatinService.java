package com.example.microservicio_monopatin.service;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.feignClient.AdministradorFeignClient;
import com.example.microservicio_monopatin.feignClient.ParadaFeignClient;
import com.example.microservicio_monopatin.feignClient.ViajeFeignClient;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_monopatin.dtos.ReporteUsoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MonopatinService {

    @Autowired
    MonopatinRepository monopatinRepository;

    @Autowired
    private AdministradorFeignClient administradorFeignClient;

    @Autowired
    private ParadaFeignClient paradaFeignClient;

    @Autowired
    private ViajeFeignClient viajeFeignClient;  // Cliente Feign para el microservicio de Viaje

    public List<MonopatinDTO> getAll() {
        List<Monopatin> monopatines = monopatinRepository.findAll();
        return monopatines.stream()
                .map(MonopatinDTO::new)  // Convierte a MonopatinDTO
                .collect(Collectors.toList());
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


    // Método para pausar el monopatín
    public void pausarMonopatin(Long monopatinId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatin no encontrado"));

        viajeFeignClient.registrarInicioPausa(monopatin.getIdViajeActivo(), LocalDateTime.now());  // Registrar pausa en Viaje
        monopatin.setDisponible(false);
        monopatinRepository.save(monopatin);
    }

    // Método para reiniciar el monopatín y calcular la tarifa de reinicio
    public void reiniciarMonopatin(Long monopatinId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatin no encontrado"));

        LocalDateTime finPausa = LocalDateTime.now();
        LocalDateTime inicioPausa = viajeFeignClient.obtenerInicioUltimaPausa(monopatinId);
        long minutosPausa = Duration.between(inicioPausa, finPausa).toMinutes();

        double tarifa = calcularTarifaReinicio(minutosPausa);
        viajeFeignClient.registrarFinPausa(monopatinId, finPausa);  // Actualizar fin de pausa en Viaje

        administradorFeignClient.aplicarTarifaExtra(monopatinId, tarifa);
        monopatinRepository.save(monopatin);
    }

    // Método para calcular la tarifa de reinicio
    private double calcularTarifaReinicio(long minutosPausa) {
        double tarifaBase = administradorFeignClient.obtenerPrecioBase();
        if (minutosPausa > 15) {
            tarifaBase += administradorFeignClient.obtenerTarifaExtra();
        }
        return tarifaBase;
    }

    // Método para iniciar un viaje al retirar el monopatín de una parada
    public void iniciarViaje(Long monopatinId, Long paradaId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatin no encontrado"));

        // Verificar que el monopatín esté disponible para iniciar el viaje
        if (monopatin.isDisponible()) {
            // Registrar el inicio del viaje en el microservicio de Viaje
            LocalDateTime fechaHoraInicio = LocalDateTime.now();
            viajeFeignClient.iniciarViaje(monopatinId, fechaHoraInicio);

            // Cambiar el estado del monopatín a no disponible
            monopatin.setDisponible(false);
            monopatinRepository.save(monopatin);
        } else {
            throw new RuntimeException("El monopatín no está disponible para iniciar un viaje");
        }
    }

    // Método para ubicar monopatín en una parada válida y finalizar el viaje si está en uso
    public boolean pararMonopatin(Long monopatinId, Long paradaId, Long viajeId, Long kmRecorridos) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId).orElse(null);
        if (monopatin != null && paradaId != null && monopatin.isDisponible()) {
            Parada parada = paradaFeignClient.getParadaById(paradaId);
            if (parada != null && esParadaPermitida(monopatin, parada)) {

                // Finalizar el viaje llamando al microservicio Viaje
                finalizarViaje(monopatinId, viajeId, kmRecorridos);

                monopatin.setDisponible(true);  // Monopatín vuelve a estar disponible
                monopatinRepository.save(monopatin);
                return true;
            }
        }
        return false;
    }


    // Método para verificar si el monopatín está en el rango de la parada
    private boolean esParadaPermitida(Monopatin monopatin, Parada parada) {
        double rangoPermitido = 0.0005; // Aproximadamente 50 m, ajustable
        return Math.abs(monopatin.getLatitud() - parada.getLatitud()) < rangoPermitido &&
                Math.abs(monopatin.getLongitud() - parada.getLongitud()) < rangoPermitido;
    }

    // Método para finalizar el viaje
    private void finalizarViaje(Long monopatinId, Long viajeId, Long kmRecorridos) {
        LocalDateTime fechaHoraFin = LocalDateTime.now();

        // Llamada al microservicio Viaje para registrar la finalización
        viajeFeignClient.finalizarViaje(viajeId, fechaHoraFin, kmRecorridos);
    }

    public Boolean habilitar(Long monopatinId) {
        Boolean habilitado = monopatinRepository.findById(monopatinId).isPresent();
        if(habilitado)
            monopatinRepository.habilitar(monopatinId);

        return habilitado;
    }

    public Boolean deshabilitar(Long monopatinId) {
        Boolean habilitado = monopatinRepository.findById(monopatinId).isPresent();
        if(habilitado)
            monopatinRepository.deshabilitar(monopatinId);

        return habilitado;
    }

    public List<ReporteUsoDto> getReporteUsoMonopatinesCompleto(){
        try {
            List<ReporteUsoDto> reporte = monopatinRepository.reporteUsoCompleto();

            if (reporte == null || reporte.isEmpty())
                return Collections.emptyList();

            return reporte;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de uso de monopatines completo", e);
        }
    }
    public List<ReporteUsoDto> getReporteUsoMonopatinesCompletoSinPausa(){
        try {
            Map<Long, Long> pausasMonopatines = (Map<Long, Long>) viajeFeignClient.getPausasMonopatines().getBody();
            List<ReporteUsoDto> reportes = monopatinRepository.reporteUsoCompleto();

            if (reportes == null || reportes.isEmpty())
                return Collections.emptyList();
            if(pausasMonopatines == null || pausasMonopatines.isEmpty())
                return reportes;

            for(ReporteUsoDto reporte : reportes){
                reporte.setTiempoTotal(
                        reporte.getTiempoTotal()
                                -
                                pausasMonopatines.get(reporte.getIdMonopatin()));
            }
            return reportes;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de uso de monopatines completo sin pausa", e);
        }
    }


    public List<ReporteUsoDto> getReporteUsoMonopatinesPorKilometro(){
        try {
            List<ReporteUsoDto> reporte = monopatinRepository.reporteUsoPorKilometro();

            if (reporte == null || reporte.isEmpty())
                return Collections.emptyList();

            return reporte;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de uso de monopatines por kilometros", e);
        }
    }

    public List<ReporteUsoDto> getReporteMonopatinesPorTiempoConPausas(){

        try {
            List<ReporteUsoDto> reportes = monopatinRepository.reporteUsoPorTiempo();
            if (reportes == null || reportes.isEmpty())
                return Collections.emptyList();

            return reportes;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de uso de monopatines por tiempo con pausas", e);
        }
    }

    public List<ReporteUsoDto> getReporteMonopatinesPorTiempoSinPausas(){

        try {
            Map<Long, Long> pausasMonopatines = (Map<Long, Long>) viajeFeignClient.getPausasMonopatines().getBody();
            List<ReporteUsoDto> reportes = monopatinRepository.reporteUsoPorTiempo();
            if (reportes == null || reportes.isEmpty())
                return Collections.emptyList();

            if(pausasMonopatines == null || pausasMonopatines.isEmpty())
                return reportes;

            for(ReporteUsoDto reporte : reportes){
                reporte.setTiempoTotal(
                        reporte.getTiempoTotal()
                        -
                        pausasMonopatines.get(reporte.getIdMonopatin()));
            }

            return reportes;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de uso de monopatines por tiempo sin pausas", e);
        }
    }
}
