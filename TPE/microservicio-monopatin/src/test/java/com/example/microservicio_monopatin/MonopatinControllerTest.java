package com.example.microservicio_monopatin;

import com.example.microservicio_monopatin.controller.MonopatinController;
import com.example.microservicio_monopatin.dtos.MonopatinDTO;
import com.example.microservicio_monopatin.dtos.ReporteUsoDto;
import com.example.microservicio_monopatin.entity.Monopatin;
import com.example.microservicio_monopatin.service.MonopatinService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonopatinController.class)
public class MonopatinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonopatinService monopatinService;

    @InjectMocks
    private MonopatinController monopatinController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(monopatinController).build();
    }

    @Test
    public void testGetAllMonopatines() throws Exception {
        when(monopatinService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/monopatines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSaveMonopatin() throws Exception {
        MonopatinDTO monopatin = new MonopatinDTO();
        monopatin.setId(1L);


        when(monopatinService.save(any(MonopatinDTO.class))).thenReturn(monopatin);

        mockMvc.perform(post("/monopatines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"kmRecorridosTotales\": 100, \"tiempoRecorridosTotales\": 120, \"disponible\": true, \"longitud\": 123.456, \"latitud\": 78.910, \"viajeActivo\": 2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    public void testGetMonopatinById() throws Exception {
        // Crea un objeto Monopatin simulado
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);

        // Crea un MonopatinDTO a partir del objeto Monopatin
        MonopatinDTO monopatinDTO = new MonopatinDTO(monopatin);

        // Simula que el servicio devuelve el MonopatinDTO
        when(monopatinService.findById(1L)).thenReturn(monopatin);

        // Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(get("/monopatines/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetMonopatinByIdNotFound() throws Exception {
        when(monopatinService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/monopatines/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMonopatin() throws Exception {
        // Crea un objeto Monopatin para simular el que se eliminará
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);

        // Simula que la búsqueda devuelve el objeto Monopatin
        when(monopatinService.findById(1L)).thenReturn(monopatin);

        // Simula que el método delete no lanza excepciones
        doNothing().when(monopatinService).delete(monopatin);

        // Realiza la solicitud DELETE y espera un estado 204 No Content
        mockMvc.perform(delete("/monopatines/1"))
                .andExpect(status().isNoContent());

        // Verifica que el método delete fue llamado exactamente una vez
        verify(monopatinService, times(1)).delete(monopatin);
    }

    @Test
    public void testHabilitarMonopatin() throws Exception {
        when(monopatinService.habilitar(1L)).thenReturn(true);

        mockMvc.perform(put("/monopatines/habilitar/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeshabilitarMonopatin() throws Exception {
        when(monopatinService.deshabilitar(1L)).thenReturn(true);

        mockMvc.perform(put("/monopatines/deshabilitar/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReservarMonopatin() throws Exception {
        MonopatinDTO monopatinDTO = new MonopatinDTO();
        monopatinDTO.setId(1L);

        when(monopatinService.iniciarViaje(1L, 1L)).thenReturn(monopatinDTO);

        mockMvc.perform(put("/monopatines/reservarMonopatin/parada/1/monopatin/1/reservar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testPausarMonopatin() throws Exception {
        doNothing().when(monopatinService).pausarMonopatin(1L);

        mockMvc.perform(put("/monopatines/pausa/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReporteUsoPorKilometro() throws Exception {
        List<ReporteUsoDto> reporte = List.of(new ReporteUsoDto(/* parámetros según tu DTO */));

        when(monopatinService.getReporteUsoMonopatinesPorKilometro()).thenReturn(reporte);

        mockMvc.perform(get("/monopatines/reporte/kilometros")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(reporte.size()));
    }

    @Test
    public void testGetReporteUsoCompleto() throws Exception {
        List<ReporteUsoDto> reporte = List.of(new ReporteUsoDto(/* parámetros según tu DTO */));

        when(monopatinService.getReporteUsoMonopatinesCompleto()).thenReturn(reporte);

        mockMvc.perform(get("/monopatines/reporte/completo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(reporte.size()));
    }

    @Test
    public void testGetReporteUsoCompletoSinPausa() throws Exception {
        List<ReporteUsoDto> reporte = List.of(new ReporteUsoDto(/* parámetros según tu DTO */));

        when(monopatinService.getReporteUsoMonopatinesCompletoSinPausa()).thenReturn(reporte);

        mockMvc.perform(get("/monopatines/reporte/completo/sinpausa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(reporte.size()));
    }
    
}