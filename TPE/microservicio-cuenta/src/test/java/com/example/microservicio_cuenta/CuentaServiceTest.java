package com.example.microservicio_cuenta;

import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.feignClients.AdministradorFeignClient;
import com.example.microservicio_cuenta.feignClients.ViajeFeignClient;
import com.example.microservicio_cuenta.models.Tarifa;
import com.example.microservicio_cuenta.models.Viaje;
import com.example.microservicio_cuenta.repository.CuentaAppRepository;
import com.example.microservicio_cuenta.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceTest {

    @Mock
    private CuentaAppRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    @Mock
    private ViajeFeignClient viajeFeignClient;

    @Mock
    private AdministradorFeignClient administradorFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCuenta() {
        CuentaApp cuenta = new CuentaApp();
        when(cuentaRepository.save(any(CuentaApp.class))).thenReturn(cuenta);

        CuentaApp savedCuenta = cuentaService.save(cuenta);
        assertNotNull(savedCuenta);
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testFindById() {
        CuentaApp cuenta = new CuentaApp();
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        CuentaApp foundCuenta = cuentaService.findById(1L);
        assertNotNull(foundCuenta);
    }

    @Test
    void testFindByIdNotFound() {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.empty());

        CuentaApp foundCuenta = cuentaService.findById(1L);
        assertNull(foundCuenta);
    }

    @Test
    void testDeleteCuenta() {
        CuentaApp cuenta = new CuentaApp();
        doNothing().when(cuentaRepository).delete(cuenta);

        cuentaService.delete(cuenta);
        verify(cuentaRepository, times(1)).delete(cuenta);
    }

    @Test
    void testHabilitarCuenta() {
        doNothing().when(cuentaRepository).habilitar(1L);

        cuentaService.habilitar(1L);
        verify(cuentaRepository, times(1)).habilitar(1L);
    }

    // Agrega más pruebas para otros métodos del servicio...
    @Test
    void testCobrarViaje() {
        Long idCuenta = 1L;
        Long idViaje = 1L;

        // Simula los datos de la cuenta
        CuentaApp cuenta = new CuentaApp();
        cuenta.setMontoCreditos(1000.0);
        when(cuentaRepository.findById(idCuenta)).thenReturn(Optional.of(cuenta));

        // Simula los datos del viaje
        Viaje viaje = new Viaje();
        viaje.setFechaHoraInicio(LocalDateTime.now().minusMinutes(60));
        viaje.setFechaHoraFin(LocalDateTime.now());
        viaje.setInicioPausasFinal(Collections.emptyList());
        viaje.setValorTotal(0.0);
        ResponseEntity<Viaje> viajeResponseEntity = ResponseEntity.ok(viaje);
        when(viajeFeignClient.getViajeById(idViaje)).thenReturn((ResponseEntity) viajeResponseEntity);

        // Simula las tarifas
        Tarifa tarifaNormal = new Tarifa();
        tarifaNormal.setPrecioTarifa(1.0);
        ResponseEntity<Tarifa> tarifaNormalResponse = ResponseEntity.ok(tarifaNormal);
        when(administradorFeignClient.getTarifaByTipo("normal")).thenReturn((ResponseEntity) tarifaNormalResponse);

        Tarifa tarifaPausa = new Tarifa();
        tarifaPausa.setPrecioTarifa(0.5);
        ResponseEntity<Tarifa> tarifaPausaResponse = ResponseEntity.ok(tarifaPausa);
        when(administradorFeignClient.getTarifaByTipo("pausa")).thenReturn((ResponseEntity) tarifaPausaResponse);

        Tarifa tarifaAumentada = new Tarifa();
        tarifaAumentada.setPrecioTarifa(2.0);
        ResponseEntity<Tarifa> tarifaAumentadaResponse = ResponseEntity.ok(tarifaAumentada);
        when(administradorFeignClient.getTarifaByTipo("aumentada")).thenReturn((ResponseEntity) tarifaAumentadaResponse);

        // Ejecuta el método
        cuentaService.cobrarViaje(idCuenta, idViaje);

        // Verifica el cálculo y la actualización del monto de créditos
        verify(cuentaRepository).save(cuenta);
        assertTrue(cuenta.getMontoCreditos() < 1000.0);
    }
}