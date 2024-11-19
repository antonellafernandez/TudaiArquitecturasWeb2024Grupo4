package com.example.microservicio_mantenimiento;

import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import com.example.microservicio_mantenimiento.feignClients.MonopatinFeignClient;
import com.example.microservicio_mantenimiento.models.Monopatin;
import com.example.microservicio_mantenimiento.repository.MantenimientoRepository;
import com.example.microservicio_mantenimiento.service.MantenimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class MantenimientoServiceTest {

    @Mock
    private MantenimientoRepository mantenimientoRepository;

    @InjectMocks
    private MantenimientoService mantenimientoService;

    @Mock
    private MonopatinFeignClient monopatinFeignClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testRegistrarMonopatinEnMantenimiento() {
        Long id = 1L;
        Long umbralKm = 1000L;
        Long umbralTiempo = 30L;
        Monopatin monopatin = new Monopatin();
        monopatin.setDisponible(true);
        monopatin.setKmRecorridosTotales(1500L);
        monopatin.setTiempoRecorridosTotales(40L);
        Mantenimiento mantenimiento = new Mantenimiento();

        // Simula el comportamiento del cliente Feign
        when(monopatinFeignClient.getMonopatinById(id)).thenReturn(monopatin);
        when(mantenimientoRepository.save(any(Mantenimiento.class))).thenReturn(mantenimiento);

        Mantenimiento result = mantenimientoService.registrarMonopatinEnMantenimiento(id, umbralKm, umbralTiempo);

        assertNotNull(result);
        // Puedes agregar más aserciones para verificar el estado de `result`
    }

    @Test
    public void testFinalizarMantenimiento() {
        Long idMantenimiento = 1L;
        Mantenimiento mantenimiento = new Mantenimiento();

        when(mantenimientoRepository.findById(idMantenimiento)).thenReturn(Optional.of(mantenimiento));
        when(mantenimientoRepository.monopatinEnMantenimiento(idMantenimiento)).thenReturn(false); // Simula que no está en mantenimiento
        when(mantenimientoRepository.finalizarMantenimiento(eq(idMantenimiento), any(LocalDateTime.class))).thenReturn(mantenimiento);
        Mantenimiento result = mantenimientoService.finalizarMantenimiento(idMantenimiento);
        assertNotNull(result);

    }
}