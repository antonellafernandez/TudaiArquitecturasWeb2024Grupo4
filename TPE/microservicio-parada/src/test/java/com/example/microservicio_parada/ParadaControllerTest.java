package com.example.microservicio_parada;

import com.example.microservicio_parada.controller.ParadaController;
import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.service.ParadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParadaController.class)
public class ParadaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParadaService paradaService;

    @InjectMocks
    private ParadaController paradaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paradaController).build();
    }

    @Test
    public void testGetAllParadas() throws Exception {
        when(paradaService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/paradas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSaveParada() throws Exception {
        Parada parada = new Parada();
        parada.setId(1L);
        parada.setNombre("Parada Test");

        when(paradaService.save(any(Parada.class))).thenReturn(parada);

        mockMvc.perform(post("/paradas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Parada Test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Parada Test"));
    }

    @Test
    public void testGetParadaByIdNotFound() throws Exception {
        when(paradaService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/paradas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteParada() throws Exception {
        Parada parada = new Parada();
        parada.setId(1L);

        when(paradaService.findById(1L)).thenReturn(parada);

        mockMvc.perform(delete("/paradas/1"))
                .andExpect(status().isNoContent());

        verify(paradaService, times(1)).delete(parada);
    }
}