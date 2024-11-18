package com.example.microservicio_monopatin;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import com.example.microservicio_monopatin.dtos.ReporteUsoDto;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import com.example.microservicio_monopatin.service.MonopatinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MonopatinServiceTest {

    @Mock
    private MonopatinRepository monopatinRepository;

    @InjectMocks
    private MonopatinService monopatinService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveMonopatin() {
        MonopatinDTO monopatinDTO = new MonopatinDTO();
        // Configura otros campos necesarios
        Monopatin monopatin = new Monopatin();
        monopatinDTO.setId(1L);
        // Configura otros campos necesarios

        when(monopatinRepository.save(any(Monopatin.class))).thenReturn(monopatin);

        MonopatinDTO result = monopatinService.save(monopatinDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        // Verifica otros campos si es necesario
    }

    @Test
    public void testGetAllMonopatines() {
        when(monopatinRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(monopatinService.getAll().isEmpty());
    }

    @Test
    public void testFindById() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);
        // Configura otros campos necesarios

        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));

        Monopatin result = monopatinService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        // Verifica otros campos si es necesario
    }

    @Test
    public void testDeleteMonopatin() {
        // Crea un objeto Monopatin simulado
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);
        // Configura otros campos necesarios si es necesario

        // Simula que el repositorio encuentra el Monopatin
        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));

        doNothing().when(monopatinRepository).delete(monopatin);

        monopatinService.delete(monopatin);

        // Verifica que el método delete fue llamado una vez con el Monopatin correcto
        verify(monopatinRepository, times(1)).delete(monopatin);
    }

    @Test
    public void testHabilitarMonopatin() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);

        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));
        when(monopatinRepository.save(any(Monopatin.class))).thenReturn(monopatin);

        boolean result = monopatinService.habilitar(1L);

        assertTrue(result);
    }

    @Test
    public void testDeshabilitarMonopatin() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);

        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));
        when(monopatinRepository.save(any(Monopatin.class))).thenReturn(monopatin);

        boolean result = monopatinService.deshabilitar(1L);

        assertTrue(result);
    }

    @Test
    public void testIniciarViaje() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);

        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));
        when(monopatinRepository.save(any(Monopatin.class))).thenReturn(monopatin);

        MonopatinDTO result = monopatinService.iniciarViaje(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testPausarMonopatin() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);

        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));

        monopatinService.pausarMonopatin(1L);

        verify(monopatinRepository, times(1)).save(monopatin);
    }

    @Test
    public void testPararMonopatin() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);

        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));

        monopatinService.pararMonopatin(1L, 1L, 1L, 10L);

        verify(monopatinRepository, times(1)).save(monopatin);
    }

    @Test
    public void testGetReporteUsoMonopatinesPorKilometro() {
        // Configura un resultado esperado
        List<ReporteUsoDto> expectedReport = List.of(new ReporteUsoDto(/* parámetros según tu DTO */));

        when(monopatinService.getReporteUsoMonopatinesPorKilometro()).thenReturn(expectedReport);

        List<ReporteUsoDto> result = monopatinService.getReporteUsoMonopatinesPorKilometro();

        assertNotNull(result);
        assertEquals(expectedReport.size(), result.size());
    }

    @Test
    public void testGetReporteUsoMonopatinesCompleto() {
        // Configura un resultado esperado
        List<ReporteUsoDto> expectedReport = List.of(new ReporteUsoDto(/* parámetros según tu DTO */));

        when(monopatinService.getReporteUsoMonopatinesCompleto()).thenReturn(expectedReport);

        List<ReporteUsoDto> result = monopatinService.getReporteUsoMonopatinesCompleto();

        assertNotNull(result);
        assertEquals(expectedReport.size(), result.size());
    }

    @Test
    public void testGetReporteUsoMonopatinesCompletoSinPausa() {
        // Configura un resultado esperado
        List<ReporteUsoDto> expectedReport = List.of(new ReporteUsoDto(/* parámetros según tu DTO */));

        when(monopatinService.getReporteUsoMonopatinesCompletoSinPausa()).thenReturn(expectedReport);

        List<ReporteUsoDto> result = monopatinService.getReporteUsoMonopatinesCompletoSinPausa();

        assertNotNull(result);
        assertEquals(expectedReport.size(), result.size());
    }
}