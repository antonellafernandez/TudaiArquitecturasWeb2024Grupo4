package com.example.microservicio_monopatin.service;

import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.feignClient.AdministracionFeignClient;
import com.example.microservicio_monopatin.feignClient.ParadaFeignClient;
import com.example.microservicio_monopatin.feignClient.ViajeFeignClient;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import com.example.microservicio_parada.entity.Parada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class MonopatinService {

    @Autowired
    MonopatinRepository monopatinRepository;

    @Autowired
    private AdministracionFeignClient administracionClient;

    @Autowired
    private ParadaFeignClient paradaClient;

    @Autowired
    private ViajeFeignClient viajeClient;  // Cliente Feign para el microservicio de Viaje

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


    // Método para pausar el monopatín
    public void pausarMonopatin(Long monopatinId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatin no encontrado"));

        viajeClient.registrarInicioPausa(monopatinId, LocalDateTime.now());  // Registrar pausa en Viaje
        monopatin.setDisponible(false);
        monopatinRepository.save(monopatin);
    }

    // Método para reiniciar el monopatín y calcular la tarifa de reinicio
    public void reiniciarMonopatin(Long monopatinId) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatin no encontrado"));

        LocalDateTime finPausa = LocalDateTime.now();
        LocalDateTime inicioPausa = viajeClient.obtenerInicioUltimaPausa(monopatinId);
        long minutosPausa = Duration.between(inicioPausa, finPausa).toMinutes();

        double tarifa = calcularTarifaReinicio(minutosPausa);
        viajeClient.registrarFinPausa(monopatinId, finPausa);  // Actualizar fin de pausa en Viaje


        administracionClient.aplicarTarifaExtra(monopatinId, tarifa);

        monopatin.setDisponible(true);
        monopatinRepository.save(monopatin);
    }

    // Método para calcular la tarifa de reinicio
    private double calcularTarifaReinicio(long minutosPausa) {
        double tarifaBase = administracionClient.obtenerPrecioBase();
        if (minutosPausa > 15) {
            tarifaBase += administracionClient.obtenerTarifaExtra();
        }
        return tarifaBase;
    }


    // Método para ubicar monopatín en una parada válida y finalizar el viaje si está en uso
    public boolean pararMonopatin(Long monopatinId, Long paradaId, Long viajeId, Long kmRecorridos) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId).orElse(null);
        if (monopatin != null && paradaId != null && monopatin.isDisponible()) {
            Parada parada = paradaClient.getParadaById(paradaId);
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
        viajeClient.finalizarViaje(viajeId, fechaHoraFin, kmRecorridos);
    }
}








