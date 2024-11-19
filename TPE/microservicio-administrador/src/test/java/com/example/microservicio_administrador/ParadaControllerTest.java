package com.example.microservicio_administrador;

import com.example.microservicio_administrador.controller.ParadaController;
import com.example.microservicio_administrador.feignClient.ParadaFeignClient;
import com.example.microservicio_administrador.model.Parada;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParadaController.class)
public class ParadaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParadaFeignClient paradaFeignClient;

    @Test
    public void testAddParada() throws Exception {
        Parada newParada = new Parada();
        newParada.setId(1L); // Configura otros campos según sea necesario

        when(paradaFeignClient.save(newParada)).thenReturn(newParada);

        mockMvc.perform(post("/administradores/paradas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}")) // Ajusta el JSON según los campos de Parada
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testHabilitarParada() throws Exception {
        Parada parada = new Parada();
        parada.setId(1L);

        when(paradaFeignClient.habilitarParada(1L)).thenReturn(parada);

        mockMvc.perform(put("/administradores/paradas/habilitar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeshabilitarParada() throws Exception {
        Parada parada = new Parada();
        parada.setId(1L);

        when(paradaFeignClient.deshabilitarParada(1L)).thenReturn(parada);

        mockMvc.perform(put("/administradores/paradas/deshabilitar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateParada() throws Exception {
        Parada updatedParada = new Parada();
        updatedParada.setId(1L);

        when(paradaFeignClient.updateParada(1L, updatedParada)).thenReturn(updatedParada);

        mockMvc.perform(put("/administradores/paradas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}")) // Ajusta el JSON según los campos de Parada
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}