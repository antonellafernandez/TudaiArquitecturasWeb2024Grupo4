package com.example.microservicio_viaje;

import com.example.microservicio_viaje.controller.ViajeController;
import com.example.microservicio_viaje.entity.Viaje;
import com.example.microservicio_viaje.service.ViajeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViajeController.class)
public class ViajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViajeService viajeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetViajeById() throws Exception {
        // Arrange
        Long id = 1L;
        Long idMonopatin = 10L;
        Long idCuenta = 20L;
        LocalDateTime fechaHoraInicio = LocalDateTime.now();
        LocalDateTime fechaHoraFin = fechaHoraInicio.plusHours(1);
        Long kmRecorridos = 5L;
        Double valorTotal = 150.0;

        Viaje expectedViaje = new Viaje();
        expectedViaje.setId(id);
        expectedViaje.setIdMonopatin(idMonopatin);
        expectedViaje.setIdCuenta(idCuenta);
        expectedViaje.setFechaHoraInicio(fechaHoraInicio);
        expectedViaje.setFechaHoraFin(fechaHoraFin);
        expectedViaje.setKmRecorridos(kmRecorridos);
        expectedViaje.setValorTotal(valorTotal);

        when(viajeService.findById(id)).thenReturn(expectedViaje);

        // Act & Assert
        mockMvc.perform(get("/viajes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.idMonopatin").value(idMonopatin))
                .andExpect(jsonPath("$.idCuenta").value(idCuenta))
                .andExpect(jsonPath("$.fechaHoraInicio").value(fechaHoraInicio.toString()))
                .andExpect(jsonPath("$.fechaHoraFin").value(fechaHoraFin.toString()))
                .andExpect(jsonPath("$.kmRecorridos").value(kmRecorridos))
                .andExpect(jsonPath("$.valorTotal").value(valorTotal));
    }

    @Test
    public void testGetViajeById_NotFound() throws Exception {
        when(viajeService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/viajes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSaveViaje() throws Exception {
        // Arrange
        Viaje viaje = new Viaje();
        viaje.setIdMonopatin(10L);
        viaje.setIdCuenta(20L);
        viaje.setFechaHoraInicio(LocalDateTime.now());
        viaje.setFechaHoraFin(LocalDateTime.now().plusHours(1));
        viaje.setKmRecorridos(5L);
        viaje.setValorTotal(150.0);

        when(viajeService.save(any(Viaje.class))).thenReturn(viaje);

        // Act & Assert
        mockMvc.perform(post("/viajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(viaje)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.idMonopatin").value(10L))
                .andExpect(jsonPath("$.idCuenta").value(20L))
                .andExpect(jsonPath("$.fechaHoraInicio").isNotEmpty())
                .andExpect(jsonPath("$.kmRecorridos").value(5L))
                .andExpect(jsonPath("$.valorTotal").value(150.0));
    }

    @Test
    public void testGetAllViajes() throws Exception {
        // Arrange
        Viaje viaje = new Viaje();
        viaje.setIdMonopatin(10L);
        viaje.setIdCuenta(20L);
        viaje.setFechaHoraInicio(LocalDateTime.now());
        viaje.setFechaHoraFin(LocalDateTime.now().plusHours(1));
        viaje.setKmRecorridos(5L);
        viaje.setValorTotal(150.0);
        List<Viaje> viajes = Collections.singletonList(viaje);

        when(viajeService.getAll()).thenReturn(viajes);

        // Act & Assert
        mockMvc.perform(get("/viajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].idMonopatin").value(10L))
                .andExpect(jsonPath("$[0].idCuenta").value(20L))
                .andExpect(jsonPath("$[0].fechaHoraInicio").isNotEmpty())
                .andExpect(jsonPath("$[0].kmRecorridos").value(5L))
                .andExpect(jsonPath("$[0].valorTotal").value(150.0));
    }

    @Test
    public void testDeleteViaje() throws Exception {
        // Arrange
        Viaje viaje = new Viaje();
        viaje.setId(1L);

        when(viajeService.findById(1L)).thenReturn(viaje);
        doNothing().when(viajeService).delete(viaje);

        // Act & Assert
        mockMvc.perform(delete("/viajes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testHabilitarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioService.findById(1L)).thenReturn(usuario);
        doNothing().when(usuarioService).habilitar(1L);

        mockMvc.perform(put("/usuarios/habilitar/1"))
                .andExpect(status().isNoContent());
    }
}
