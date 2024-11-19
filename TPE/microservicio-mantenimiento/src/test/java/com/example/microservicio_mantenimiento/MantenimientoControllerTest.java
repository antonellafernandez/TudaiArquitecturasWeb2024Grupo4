package com.example.microservicio_mantenimiento;

import com.example.microservicio_mantenimiento.controller.MantenimientoController;
import com.example.microservicio_mantenimiento.dto.MantenimientoDto;
import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import com.example.microservicio_mantenimiento.service.MantenimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MantenimientoController.class)
public class MantenimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MantenimientoService mantenimientoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegistrarMantenimiento() throws Exception {
        Long id = 1L;
        Long umbralKm = 1000L;
        Long umbralTiempo = 30L;
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId(id); // Asegúrate de que el mantenimiento tenga un ID

        // Simula el comportamiento del servicio
        when(mantenimientoService.registrarMonopatinEnMantenimiento(eq(id), eq(umbralKm), eq(umbralTiempo)))
                .thenReturn(mantenimiento);

        // Crea el objeto JSON para enviar en la solicitud
        MantenimientoDto request = new MantenimientoDto();
        request.setUmbralKm(umbralKm);
        request.setUmbralTiempo(umbralTiempo);

        mockMvc.perform(post("/mantenimientos/registrar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Envía el objeto como contenido
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id)) // Ejemplo de asertación adicional
                .andDo(print());
    }

    @Test
    public void testFinalizarMantenimiento() throws Exception {
        Long idMantenimiento = 1L;
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId(idMantenimiento); // Asegúrate de que el mantenimiento tenga un ID

        when(mantenimientoService.finalizarMantenimiento(idMantenimiento)).thenReturn(mantenimiento);

        mockMvc.perform(put("/mantenimientos/finalizar/{idMantenimiento}", idMantenimiento))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(idMantenimiento)) // Asegúrate de que el ID se verifique
                .andDo(print());
    }
}