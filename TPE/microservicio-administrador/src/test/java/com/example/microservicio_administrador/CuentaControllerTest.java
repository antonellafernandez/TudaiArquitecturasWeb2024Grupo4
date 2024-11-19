package com.example.microservicio_administrador;

import com.example.microservicio_administrador.controller.CuentaController;
import com.example.microservicio_administrador.feignClient.CuentaFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaFeignClient cuentaFeignClient;

    @Test
    public void testHabilitarCuenta() throws Exception {
        Long cuentaId = 1L;

        // Simula una respuesta exitosa del cliente Feign
        when(cuentaFeignClient.habilitarCuenta(cuentaId)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/api/administradores/cuentas/habilitar/" + cuentaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeshabilitarCuenta() throws Exception {
        Long cuentaId = 1L;

        // Simula una respuesta exitosa del cliente Feign
        when(cuentaFeignClient.deshabilitarCuenta(cuentaId)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/api/administradores/cuentas/deshabilitar/" + cuentaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testHabilitarCuentaThrowsException() throws Exception {
        Long cuentaId = 1L;

        // Simula una excepción Feign
        doThrow(FeignException.class).when(cuentaFeignClient).habilitarCuenta(cuentaId);

        mockMvc.perform(put("/api/administradores/cuentas/habilitar/" + cuentaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaId.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeshabilitarCuentaThrowsException() throws Exception {
        Long cuentaId = 1L;

        // Simula una excepción Feign
        doThrow(FeignException.class).when(cuentaFeignClient).deshabilitarCuenta(cuentaId);

        mockMvc.perform(put("/api/administradores/cuentas/deshabilitar/" + cuentaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaId.toString()))
                .andExpect(status().isBadRequest());
    }
}