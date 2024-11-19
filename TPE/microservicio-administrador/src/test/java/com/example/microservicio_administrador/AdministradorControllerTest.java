package com.example.microservicio_administrador;

import com.example.microservicio_administrador.controller.AdministradorController;
import com.example.microservicio_administrador.dto.AdministradorDto;
import com.example.microservicio_administrador.feignClient.ViajeFeignClient;
import com.example.microservicio_administrador.model.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.microservicio_administrador.service.AdministradorService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdministradorController.class)
public class AdministradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdministradorService administradorService;

    @MockBean
    private ViajeFeignClient viajeFeignClient;

    @InjectMocks
    private AdministradorController administradorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(administradorController).build();
    }

    @Test
    public void testGetAllAdministradores() throws Exception {
        when(administradorService.getAllAdministradores()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/administradores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAdministradorById() throws Exception {
        AdministradorDto adminDto = new AdministradorDto();
        adminDto.setId(1L);
        adminDto.setNombre("Admin1");

        when(administradorService.getAdministradorById(1L)).thenReturn(adminDto);

        mockMvc.perform(get("/administradores/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Admin1"));
    }

    @Test
    public void testSaveAdministrador() throws Exception {
        AdministradorDto adminDto = new AdministradorDto();
        adminDto.setNombre("Admin1");

        when(administradorService.save(any(AdministradorDto.class))).thenReturn(adminDto);

        mockMvc.perform(post("/administradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Admin1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Admin1"));
    }


    @Test
    public void testDeleteAdministrador() throws Exception {
        AdministradorDto adminDto = new AdministradorDto();
        adminDto.setId(1L);
        adminDto.setNombre("Admin1");

        // Simular el comportamiento del método delete para devolver un AdministradorDto
        when(administradorService.delete(1L)).thenReturn(adminDto);

        mockMvc.perform(delete("/administradores/1"))
                .andExpect(status().isNoContent());

        // Verificar que el método delete del servicio fue llamado
        verify(administradorService, times(1)).delete(1L);
    }

    @Test
    public void testGetReporteTotalFacturadoEntreMesesDeAnio() throws Exception {
        ReporteTotalFacturadoEntreMesesDeAnio reporte = new ReporteTotalFacturadoEntreMesesDeAnio();


        when(administradorService.getReporteTotalFacturadoEntreMesesDeAnio(1L, 12L, 2023L)).thenReturn(reporte);

        mockMvc.perform(get("/administradores/reporteTotalFacturadoEntreMesesDeAnio")
                        .param("mesInicio", "1")
                        .param("mesFin", "12")
                        .param("anio", "2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}