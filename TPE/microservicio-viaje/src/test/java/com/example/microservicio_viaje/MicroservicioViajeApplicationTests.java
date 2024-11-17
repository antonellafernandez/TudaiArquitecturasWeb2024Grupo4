package com.example.microservicio_viaje;

import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.repository.ViajeRepository;
import com.example.microservicio_viaje.service.ViajeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MicroservicioViajeApplicationTests {

    @Mock
    private ViajeRepository viajeRepository;

    @InjectMocks
    private ViajeService viajeService;

    @Test
    void contextLoads() {
        // Este test verifica si la aplicación se carga correctamente.
    }

    // Test para iniciarViaje
    @Test
    void iniciarViaje_shouldCreateAndSaveViaje() {
        // Arrange
        Long monopatinId = 1L;
        LocalDateTime fechaHoraInicio = LocalDateTime.now();
        Viaje expectedViaje = new Viaje();
        expectedViaje.setIdMonopatin(monopatinId);
        expectedViaje.setFechaHoraInicio(fechaHoraInicio);

        when(viajeRepository.save(any(Viaje.class))).thenReturn(expectedViaje);

        // Act
        Viaje result = viajeService.iniciarViaje(monopatinId, fechaHoraInicio);

        // Assert
        assertNotNull(result);
        assertEquals(monopatinId, result.getIdMonopatin());
        assertEquals(fechaHoraInicio, result.getFechaHoraInicio());

        // Verify repository call
        ArgumentCaptor<Viaje> captor = ArgumentCaptor.forClass(Viaje.class);
        verify(viajeRepository, times(1)).save(captor.capture());

        Viaje capturedViaje = captor.getValue();
        assertEquals(monopatinId, capturedViaje.getIdMonopatin());
        assertEquals(fechaHoraInicio, capturedViaje.getFechaHoraInicio());
        assertNull(capturedViaje.getFechaHoraFin());
        assertEquals(0L, capturedViaje.getKmRecorridos());
    }

    // Test para finalizarViaje
    @Test
    void finalizarViaje_shouldUpdateViajeWithCorrectData() {
        // Arrange
        Long viajeId = 1L;
        LocalDateTime fechaHoraFin = LocalDateTime.now();
        Long kmRecorridos = 100L;

        Viaje existingViaje = new Viaje();
        existingViaje.setId(viajeId);
        existingViaje.setFechaHoraInicio(LocalDateTime.now().minusHours(1));

        when(viajeRepository.findById(viajeId)).thenReturn(java.util.Optional.of(existingViaje));

        // Act
        viajeService.finalizarViaje(viajeId, fechaHoraFin, kmRecorridos);

        // Assert
        assertEquals(fechaHoraFin, existingViaje.getFechaHoraFin());
        assertEquals(kmRecorridos, existingViaje.getKmRecorridos());

        // Verify that the repository was called to save the updated viaje
        verify(viajeRepository, times(1)).save(existingViaje);
    }

    // Test para manejar excepciones
    @Test
    void finalizarViaje_shouldThrowExceptionIfViajeNotFound() {
        // Arrange
        Long viajeId = 1L;

        when(viajeRepository.findById(viajeId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            viajeService.finalizarViaje(viajeId, LocalDateTime.now(), 100L);
        });

        verify(viajeRepository, never()).save(any());
    }

    // Test para registrarPausa
    @Test
    void registrarPausa_shouldAddPausaToViaje() {
        // Arrange
        Long viajeId = 1L;
        LocalDateTime fechaHoraPausa = LocalDateTime.now();

        Viaje existingViaje = new Viaje();
        existingViaje.setId(viajeId);
        existingViaje.setInicioPausasFinal(new ArrayList<>());

        when(viajeRepository.findById(viajeId)).thenReturn(java.util.Optional.of(existingViaje));

        // Act
        viajeService.registrarPausa(viajeId, fechaHoraPausa);

        // Assert
        assertEquals(1, existingViaje.getInicioPausasFinal().size());
        assertEquals(fechaHoraPausa, existingViaje.getInicioPausasFinal().get(0).getPausa());

        // Verify the save operation
        verify(viajeRepository, times(1)).save(existingViaje);
    }

    // Test para getReporteTotalFacturadoEntreMesesDeAnio
    @Test
    void getReporteTotalFacturadoEntreMesesDeAnio_shouldReturnReporte() {
        // Arrange
        Long mesInicio = 1L;
        Long mesFin = 3L;
        Long anio = 2024L;

        ReporteTotalFacturadoEntreMesesDeAnio reporte = new ReporteTotalFacturadoEntreMesesDeAnio();
        reporte.setMesInicio(mesInicio);
        reporte.setMesFin(mesFin);
        reporte.setAnio(anio);
        reporte.setTotalFacturado(1500.50);

        when(viajeService.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio)).thenReturn(reporte);

        // Act & Assert
        ResponseEntity<ReporteTotalFacturadoEntreMesesDeAnio> response = viajeController.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1500.50, response.getBody().getTotalFacturado());
    }
}
