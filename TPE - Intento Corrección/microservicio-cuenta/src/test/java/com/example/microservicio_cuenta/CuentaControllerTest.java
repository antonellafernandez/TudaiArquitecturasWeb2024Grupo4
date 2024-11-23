package com.example.microservicio_cuenta;

import com.example.microservicio_cuenta.controller.CuentaController;
import com.example.microservicio_cuenta.entity.CuentaApp;
import com.example.microservicio_cuenta.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CuentaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cuentaController).build();
    }

    @Test
    void testSaveCuenta() throws Exception {
        CuentaApp cuenta = new CuentaApp();
        when(cuentaService.save(any(CuentaApp.class))).thenReturn(cuenta);

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // En un caso real, aquí incluirías JSON válido
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllCuentas() throws Exception {
        List<CuentaApp> cuentas = Arrays.asList(new CuentaApp(), new CuentaApp());
        when(cuentaService.getAll()).thenReturn(cuentas);

        mockMvc.perform(get("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cuentas.size()));
    }

    @Test
    void testGetAllCuentasHabilitadas() throws Exception {
        List<CuentaApp> cuentas = Arrays.asList(new CuentaApp(), new CuentaApp());
        when(cuentaService.getAllHabilitadas()).thenReturn(cuentas);

        mockMvc.perform(get("/cuentas/habilitadas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cuentas.size()));
    }

    @Test
    void testGetCuentaById() throws Exception {
        CuentaApp cuenta = new CuentaApp();
        when(cuentaService.findById(1L)).thenReturn(cuenta);

        mockMvc.perform(get("/cuentas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCuentaByIdNotFound() throws Exception {
        when(cuentaService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/cuentas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCuenta() throws Exception {
        CuentaApp cuenta = new CuentaApp();
        when(cuentaService.findById(1L)).thenReturn(cuenta);
        doNothing().when(cuentaService).delete(cuenta);

        mockMvc.perform(delete("/cuentas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCuentaNotFound() throws Exception {
        when(cuentaService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/cuentas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testHabilitarCuenta() throws Exception {
        CuentaApp cuenta = new CuentaApp();
        when(cuentaService.findById(1L)).thenReturn(cuenta);
        doNothing().when(cuentaService).habilitar(1L);

        mockMvc.perform(put("/cuentas/habilitar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeshabilitarCuenta() throws Exception {
        CuentaApp cuenta = new CuentaApp();
        when(cuentaService.findById(1L)).thenReturn(cuenta);
        doNothing().when(cuentaService).deshabilitar(1L);

        mockMvc.perform(put("/cuentas/deshabilitar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCobrarViaje() throws Exception {
        doNothing().when(cuentaService).cobrarViaje(1L, 1L);

        mockMvc.perform(put("/cuentas/cobrarViaje")
                        .param("idCuenta", "1")
                        .param("idViaje", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}