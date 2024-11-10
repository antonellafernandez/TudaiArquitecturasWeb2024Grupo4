package com.example.microservicio_viaje.service;

import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ViajeService {

    @Autowired
    ViajeRepository viajeRepository;
/*
    @Autowired
    private CuentaAppRepository cuentaRepository;

    @Autowired
    private MonopatinRepository monopatinRepository;

    @Autowired
    private ParadaService paradaService;
*/

    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    public Viaje save(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    public void delete(Viaje viaje) {
        viajeRepository.delete(viaje);
    }

    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    public Viaje update(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

/*

public Viaje iniciarViaje(Long cuentaId, Long monopatinId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (cuenta.getSaldo() <= 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Lógica para iniciar el viaje
        Viaje viaje = new Viaje();

        viaje.setMonopatinId(monopatinId);
        viaje.setFechaHoraInicio(LocalDateTime.now());
        return viajeRepository.save(viaje);
    }

    public Viaje finalizarViaje(Long viajeId, double latitud, double longitud) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Obtener el monopatín asociado al viaje
        Monopatin monopatin = monopatinRepository.findMonopatinByViajeId(viajeId);
        if (monopatin == null) {
            throw new RuntimeException("Monopatín no encontrado para el viaje");
        }

        // Obtener el usuario asociado al monopatín
        Usuario usuario = monopatin.getUsuario();
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado para el monopatín");
        }

        // Verificar que el monopatín esté en una de sus paradas válidas
        if (!paradaService.esParadaValidaParaMonopatin(monopatin.getId(), latitud, longitud)) {
            throw new RuntimeException("El monopatín no está en una parada válida");
        }

        // Establecer la fecha y hora de finalización del viaje
        viaje.setFechaHoraFin(LocalDateTime.now());

        // Calcular el costo del viaje en centavos
        long costo = calcularCosto(viaje);

        // Obtener la cuenta asociada al usuario
        Cuenta cuenta = usuario.getCuenta();
        if (cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada para el usuario");
        }

        // Descontar el costo del saldo
        long nuevoSaldo = cuenta.getMontoCreditos() - costo;
        if (nuevoSaldo < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        cuenta.setMontoCreditos(nuevoSaldo);
        cuentaRepository.save(cuenta);

        // Marcar como no disponible el monopatín
        monopatin.setDisponible(false);
        monopatinRepository.save(monopatin);

        return viajeRepository.save(viaje);
    }

    private long calcularCosto(Viaje viaje) {
        // Implementar la lógica para calcular el costo del viaje
        long minutos = Duration.between(viaje.getFechaHoraInicio(), viaje.getFechaHoraFin()).toMinutes();
        long tarifaPorMinuto = 50;
        return tarifaPorMinuto * minutos;
    }

    */

}