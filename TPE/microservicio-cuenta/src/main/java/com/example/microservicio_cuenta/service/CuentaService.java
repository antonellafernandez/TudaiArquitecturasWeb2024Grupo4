package com.example.microservicio_cuenta.service;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.feignClients.AdministradorFeignClient;
import com.example.microservicio_cuenta.feignClients.UsuarioFeignClient;
import com.example.microservicio_cuenta.feignClients.ViajeFeignClient;
import com.example.microservicio_cuenta.models.Tarifa;
import com.example.microservicio_cuenta.models.Usuario;
import com.example.microservicio_cuenta.models.Viaje;
import com.example.microservicio_cuenta.repository.CuentaAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CuentaService {

    @Autowired
    CuentaAppRepository cuentaRepository;

    @Autowired
    UsuarioFeignClient usuarioFeignClient;

    @Autowired
    ViajeFeignClient viajeFeignClient;

    @Autowired
    AdministradorFeignClient administradorFeignClient;

    // Create
    @Transactional
    public CuentaApp save(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Read
    @Transactional(readOnly = true)
    public List<CuentaApp> getAll() {
        return cuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CuentaApp> getAllHabilitadas() {
        return cuentaRepository.findAllHabilitadas();
    }

    @Transactional(readOnly = true)
    public List<CuentaApp> getAllDeshabilitadas() {
        return cuentaRepository.findAllDeshabilitadas();
    }

    @Transactional(readOnly = true)
    public CuentaApp findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    // Update
    @Transactional
    public CuentaApp update(CuentaApp cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Delete
    @Transactional
    public void delete(CuentaApp cuenta) {
        cuentaRepository.delete(cuenta);
    }

    // Habilitar Cuenta
    @Transactional
    public void habilitar(Long id) {
        cuentaRepository.habilitar(id);
    }

    // Habilitar Cuenta
    @Transactional
    public void deshabilitar(Long id) {
        cuentaRepository.deshabilitar(id);
    }

    // Read Usuarios
    @Transactional(readOnly = true)
    public List<Usuario> getUsuariosById(Long id) {
        List<Usuario> salida = new ArrayList<Usuario>();
        List<Long> idUsuarios = cuentaRepository.getIdUsuarios(id);

        for (Long idUsuario : idUsuarios) {
            salida.add(usuarioFeignClient.getUsuarioById(idUsuario));
        }

        return salida;
    }

    public void cobrarViaje(Long idCuenta, Long idViaje){
        try{
            CuentaApp cuenta = cuentaRepository.findById(idCuenta).orElse(null);
            Viaje viaje = (Viaje) viajeFeignClient.getViajeById(idViaje).getBody();
            Tarifa tarifaNormal = (Tarifa) administradorFeignClient.getTarifaByTipo("normal").getBody();
            Tarifa tarifaPausa = (Tarifa) administradorFeignClient.getTarifaByTipo("pausa").getBody();
            Tarifa tarifaAumentada = (Tarifa) administradorFeignClient.getTarifaByTipo("aumentada").getBody();
            List<LocalDateTime> pausas = viaje.getInicioPausasFinal().stream()
                    .map( pausa -> pausa.getPausa())
                    .collect(Collectors.toList());

            Long duracionPausa = 0L;
            Long excedente = 0L;
            if (pausas != null && !pausas.isEmpty())
                for (int i = 0; i < pausas.size() - 1; i += 2) {
                    if (duracionPausa > 15){
                        excedente = duracionPausa - 15;
                        duracionPausa = 15L;
                        //Suma precio tarifa normal
                        Long restoNormal = Duration.between(viaje.getFechaHoraInicio(), pausas.get(i)).toMinutes();
                        viaje.setValorTotal(viaje.getValorTotal() + restoNormal * tarifaNormal.getPrecioTarifa());

                        //Suma precio tarifa en pausa
                        viaje.setValorTotal(viaje.getValorTotal() + duracionPausa * tarifaPausa.getPrecioTarifa());

                        //Suma precio tarifa aumentada al sobrepasar el limite de pausa
                        viaje.setValorTotal(viaje.getValorTotal() + excedente * tarifaAumentada.getPrecioTarifa());
                        Long restoAumentado = Duration.between(pausas.get(i + 1), viaje.getFechaHoraFin()).toMinutes();
                        viaje.setValorTotal(viaje.getValorTotal() + restoAumentado * tarifaAumentada.getPrecioTarifa());
                        break;
                    }else
                        duracionPausa += Duration.between(pausas.get(i), pausas.get(i + 1)).toMinutes();
                }
            //Suma precio tarifa en pausa
            Double precioPausa = duracionPausa * tarifaPausa.getPrecioTarifa();
            viaje.setValorTotal(viaje.getValorTotal() + precioPausa);

            //Suma precio tarifa normal
            Long tiempoNormal = Duration.between(viaje.getFechaHoraInicio(), viaje.getFechaHoraFin()).toMinutes() - duracionPausa;
            viaje.setValorTotal(viaje.getValorTotal() + tiempoNormal * tarifaNormal.getPrecioTarifa());

            cuenta.setMontoCreditos(cuenta.getMontoCreditos() - viaje.getValorTotal());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private Double getPrecioTotal(Long idViaje){
        return 0.0;
    }
}