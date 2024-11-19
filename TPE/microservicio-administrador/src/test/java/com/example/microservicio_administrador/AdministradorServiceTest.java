package com.example.microservicio_administrador;

import com.example.microservicio_administrador.dto.AdministradorDto;
import com.example.microservicio_administrador.entity.Administrador;
import com.example.microservicio_administrador.feignClient.ViajeFeignClient;
import com.example.microservicio_administrador.model.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.microservicio_administrador.repository.AdministradorRepository;
import com.example.microservicio_administrador.service.AdministradorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AdministradorServiceTest {

    @Mock
    private AdministradorRepository administradorRepository;

    @InjectMocks
    private AdministradorService administradorService;

    @Mock
    private ViajeFeignClient viajeFeignClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAdministradores() {
        Administrador admin = new Administrador();
        admin.setNombre("Admin1");

        when(administradorRepository.findAll()).thenReturn(List.of(admin));

        List<AdministradorDto> result = administradorService.getAllAdministradores();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Admin1", result.get(0).getNombre());
    }

    @Test
    public void testGetAdministradorById() {
        Administrador admin = new Administrador();
        admin.setId(1L);
        admin.setNombre("Admin1");

        when(administradorRepository.findById(1L)).thenReturn(Optional.of(admin));

        AdministradorDto result = administradorService.getAdministradorById(1L);

        assertNotNull(result);
        assertEquals("Admin1", result.getNombre());
    }

    @Test
    public void testSaveAdministrador() {
        AdministradorDto adminDto = new AdministradorDto();
        adminDto.setNombre("Admin1");

        Administrador admin = new Administrador();
        admin.setNombre("Admin1");

        when(administradorRepository.save(any(Administrador.class))).thenReturn(admin);

        AdministradorDto result = administradorService.save(adminDto);

        assertNotNull(result);
        assertEquals("Admin1", result.getNombre());
    }

    @Test
    public void testDeleteAdministrador() {
        when(administradorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(administradorRepository).deleteById(1L);

        administradorService.delete(1L);

        verify(administradorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetReporteTotalFacturadoEntreMesesDeAnio() {
        // Configura el objeto esperado que el cliente Feign debe devolver
        ReporteTotalFacturadoEntreMesesDeAnio expectedReporte = new ReporteTotalFacturadoEntreMesesDeAnio();

        // Simula la llamada al cliente Feign
        when(viajeFeignClient.getReporteTotalFacturadoEntreMesesDeAnio(1L, 12L, 2023L)).thenReturn(expectedReporte);

        // Llama al método en el servicio
        ReporteTotalFacturadoEntreMesesDeAnio actualReporte = administradorService.getReporteTotalFacturadoEntreMesesDeAnio(1L, 12L, 2023L);

        // Verifica que el resultado sea el esperado
        assertEquals(expectedReporte, actualReporte);

    }
}