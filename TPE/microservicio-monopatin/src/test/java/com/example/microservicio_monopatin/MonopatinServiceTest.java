package com.example.microservicio_monopatin;

import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.repository.MonopatinRepository;
import com.example.microservicio_monopatin.service.MonopatinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
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
}