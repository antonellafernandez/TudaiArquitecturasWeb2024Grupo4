package com.example.microservicio_viaje;

import com.example.microservicio_viaje.controller.ViajeController;
import com.example.microservicio_viaje.dto.ReporteMonopatinesPorViajesYAnio;
import com.example.microservicio_viaje.dto.ReporteTotalFacturadoEntreMesesDeAnio;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(ViajeController.class)
public class MicroservicioViajeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViajeService viajeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveViaje() throws Exception {
        // Arrange
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        viaje.setIdMonopatin(10L);
        viaje.setIdCuenta(20L);
        viaje.setFechaHoraInicio(LocalDateTime.now());
        viaje.setFechaHoraFin(LocalDateTime.now().plusHours(1));
        viaje.setKmRecorridos(5L);
        viaje.setValorTotal(150.0);

        when(viajeService.save(any(Viaje.class))).thenReturn(viaje);

        // Act & Assert
        mockMvc.perform(post("/api/viajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(viaje)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.idMonopatin").value(10L))
                .andExpect(jsonPath("$.idCuenta").value(20L))
                .andExpect(jsonPath("$.fechaHoraInicio").isNotEmpty())
                .andExpect(jsonPath("$.kmRecorridos").value(5L))
                .andExpect(jsonPath("$.valorTotal").value(150.0));
    }

    @Test
    public void testGetAllViajes() throws Exception {
        // Arrange
        Viaje viaje1 = new Viaje();
        viaje1.setId(1L);
        viaje1.setIdMonopatin(10L);
        viaje1.setIdCuenta(20L);
        viaje1.setFechaHoraInicio(LocalDateTime.now());
        viaje1.setFechaHoraFin(LocalDateTime.now().plusHours(1));
        viaje1.setKmRecorridos(5L);
        viaje1.setValorTotal(150.0);

        Viaje viaje2 = new Viaje();
        viaje2.setId(2L);
        viaje2.setIdMonopatin(15L);
        viaje2.setIdCuenta(25L);
        viaje2.setFechaHoraInicio(LocalDateTime.now().minusDays(1));
        viaje2.setFechaHoraFin(LocalDateTime.now().minusDays(1).plusHours(2));
        viaje2.setKmRecorridos(10L);
        viaje2.setValorTotal(300.0);

        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);

        when(viajeService.getAll()).thenReturn(viajes);

        // Act & Assert
        mockMvc.perform(get("/api/viajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].idMonopatin").value(10L))
                .andExpect(jsonPath("$[0].idCuenta").value(20L))
                .andExpect(jsonPath("$[0].fechaHoraInicio").isNotEmpty())
                .andExpect(jsonPath("$[0].kmRecorridos").value(5L))
                .andExpect(jsonPath("$[0].valorTotal").value(150.0))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].idMonopatin").value(15L))
                .andExpect(jsonPath("$[1].idCuenta").value(25L))
                .andExpect(jsonPath("$[1].fechaHoraInicio").isNotEmpty())
                .andExpect(jsonPath("$[1].kmRecorridos").value(10L))
                .andExpect(jsonPath("$[1].valorTotal").value(300.0));
    }

    @Test
    public void testGetViajeById() throws Exception {
        // Arrange
        Long id = 1L;
        Long idMonopatin = 10L;
        Long idCuenta = 20L;
        LocalDateTime fechaHoraInicio = LocalDateTime.now().withNano(0); // Truncar nanos
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
        mockMvc.perform(get("/api/viajes/" + id))
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

        mockMvc.perform(get("/api/viajes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteViaje() throws Exception {
        // Arrange
        Viaje viaje = new Viaje();
        viaje.setId(1L);

        when(viajeService.findById(1L)).thenReturn(viaje);
        doNothing().when(viajeService).delete(viaje);

        // Act & Assert
        mockMvc.perform(delete("/api/viajes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAsociarCuenta() throws Exception {
        // Arrange
        Long idViaje = 1L;
        Long idCuenta = 2L;

        // Crear un objeto viaje
        Viaje viaje = new Viaje();
        viaje.setId(idViaje);

        // Simulamos la respuesta del repositorio cuando buscamos el viaje
        when(viajeService.findById(idViaje)).thenReturn(viaje);

        // Simulamos que el servicio de asociar cuenta se ejecuta y actualiza el idCuenta
        doAnswer(invocation -> {
            // Aquí actualizamos el objeto viaje con el idCuenta
            viaje.setIdCuenta(idCuenta);
            return null;
        }).when(viajeService).asociarCuenta(idViaje, idCuenta);

        // Act & Assert
        mockMvc.perform(put("/api/viajes/asociarCuenta")
                        .param("idViaje", String.valueOf(idViaje))
                        .param("idCuenta", String.valueOf(idCuenta)))
                .andExpect(status().isOk());  // Esperamos que la respuesta sea 200 OK

        // Verificar que el método asociarCuenta fue llamado
        verify(viajeService, times(1)).asociarCuenta(idViaje, idCuenta);

        // Verificar que el idCuenta ha sido correctamente asociado al viaje
        assertEquals(idCuenta, viaje.getIdCuenta());
    }

    @Test
    public void testRegistrarPausa() throws Exception {
        // Arrange
        Long idViaje = 1L;
        LocalDateTime fechaHora = LocalDateTime.now();

        // Simulamos el comportamiento del servicio
        doNothing().when(viajeService).registrarPausa(idViaje, fechaHora);

        // Act & Assert
        mockMvc.perform(post("/api/viajes/registrarPausa")
                        .param("id", String.valueOf(idViaje))
                        .param("fechaHora", fechaHora.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testObtenerInicioUltimaPausa() throws Exception {
        // Arrange
        Long monopatinId = 1L;
        LocalDateTime ultimaPausa = LocalDateTime.now().minusMinutes(30).withNano(0);  // Truncamos los nanosegundos

        // Simulamos el comportamiento del servicio
        when(viajeService.obtenerInicioUltimaPausa(monopatinId)).thenReturn(ultimaPausa);

        // Act & Assert
        mockMvc.perform(get("/api/viajes/obtenerInicioUltimaPausa")
                        .param("monopatinId", String.valueOf(monopatinId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ultimaPausa.toString()));  // Verificamos que la respuesta sea la fecha y hora de la última pausa
    }

    @Test
    public void testFinalizarViaje() throws Exception {
        // Arrange
        Long viajeId = 1L;
        LocalDateTime fechaHoraFin = LocalDateTime.now().withNano(0);  // Truncamos los nanosegundos
        Long kmRecorridos = 10L;

        // Simulamos el comportamiento del servicio
        doNothing().when(viajeService).finalizarViaje(viajeId, fechaHoraFin, kmRecorridos);

        // Act & Assert
        mockMvc.perform(put("/api/viajes/finalizar")
                        .param("viajeId", String.valueOf(viajeId))
                        .param("fechaHoraFin", fechaHoraFin.toString())
                        .param("kmRecorridos", String.valueOf(kmRecorridos)))
                .andExpect(status().isOk());
    }

    @Test
    public void testIniciarViaje() throws Exception {
        // Arrange
        Long monopatinId = 1L;
        LocalDateTime fechaHoraInicio = LocalDateTime.now().withNano(0);  // Truncamos los nanosegundos
        Viaje viajeEsperado = new Viaje();
        viajeEsperado.setId(1L);
        viajeEsperado.setIdMonopatin(monopatinId);
        viajeEsperado.setFechaHoraInicio(fechaHoraInicio);

        // Simulamos el comportamiento del servicio
        when(viajeService.iniciarViaje(monopatinId, fechaHoraInicio)).thenReturn(viajeEsperado);

        // Act & Assert
        mockMvc.perform(post("/api/viajes/iniciar")
                        .param("monopatinId", String.valueOf(monopatinId))
                        .param("fechaHoraInicio", fechaHoraInicio.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))  // Verificamos que el ID del viaje es 1
                .andExpect(jsonPath("$.idMonopatin").value(monopatinId))
                .andExpect(jsonPath("$.fechaHoraInicio").value(fechaHoraInicio.toString()));  // Verificamos que la fechaHoraInicio es la correcta
    }

    @Test
    public void testGetPausasMonopatines() throws Exception {
        // Arrange
        Map<Long, Long> tiempoPausas = new HashMap<>();
        tiempoPausas.put(1L, 120L);  // 120 minutos de pausas para el monopatín 1
        tiempoPausas.put(2L, 90L);   // 90 minutos de pausas para el monopatín 2

        // Simulamos el comportamiento del servicio
        when(viajeService.getDuracionPausas()).thenReturn(tiempoPausas);

        // Act & Assert
        mockMvc.perform(get("/api/viajes/totalPausas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['1']").value(120))  // Verificamos el monopatín 1
                .andExpect(jsonPath("$['2']").value(90));  // Verificamos el monopatín 2
    }

    @Test
    public void testGetMonopatinesByCantidadViajesYAnio() throws Exception {
        // Arrange
        Long cantViajes = 5L;
        Long anio = 2023L;
        List<ReporteMonopatinesPorViajesYAnio> reporte = new ArrayList<>();
        reporte.add(new ReporteMonopatinesPorViajesYAnio(1L, 10L, anio));  // 10 viajes para el monopatín 1 en 2023
        reporte.add(new ReporteMonopatinesPorViajesYAnio(2L, 8L, anio));   // 8 viajes para el monopatín 2 en 2023

        // Simulamos el comportamiento del servicio
        when(viajeService.getReportePorViajeYAnio(cantViajes, anio)).thenReturn(reporte);

        // Act & Assert
        mockMvc.perform(get("/api/viajes/monopatines/anio/{anio}/cantViajes/{cantViajes}", anio, cantViajes))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMonopatin").value(1L))  // Verificamos que el monopatín 1 tiene 10 viajes
                .andExpect(jsonPath("$[0].cantViajes").value(10L))
                .andExpect(jsonPath("$[1].idMonopatin").value(2L))  // Verificamos que el monopatín 2 tiene 8 viajes
                .andExpect(jsonPath("$[1].cantViajes").value(8L));
    }

    @Test
    public void testGetReporteTotalFacturadoEntreMesesDeAnio() throws Exception {
        // Arrange
        Long mesInicio = 1L;
        Long mesFin = 3L;
        Long anio = 2023L;
        Double totalFacturado = 5000.0;  // Valor total facturado entre los meses de enero y marzo de 2023

        ReporteTotalFacturadoEntreMesesDeAnio reporte = new ReporteTotalFacturadoEntreMesesDeAnio(totalFacturado, anio, mesInicio, mesFin);

        // Simulamos el comportamiento del servicio
        when(viajeService.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio)).thenReturn(reporte);

        // Act & Assert
        mockMvc.perform(get("/api/viajes/facturado")
                        .param("mesInicio", String.valueOf(mesInicio))
                        .param("mesFin", String.valueOf(mesFin))
                        .param("anio", String.valueOf(anio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalFacturado").value(totalFacturado))  // Verificamos que el total facturado es 5000.0
                .andExpect(jsonPath("$.anio").value(anio))  // Verificamos que el año es 2023
                .andExpect(jsonPath("$.mesInicio").value(mesInicio))  // Verificamos que el mes de inicio es 1 (Enero)
                .andExpect(jsonPath("$.mesFin").value(mesFin));  // Verificamos que el mes de fin es 3 (Marzo)
    }
}
