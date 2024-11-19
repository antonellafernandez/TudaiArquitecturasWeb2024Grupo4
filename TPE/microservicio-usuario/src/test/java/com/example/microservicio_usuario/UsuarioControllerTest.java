package com.example.microservicio_usuario;

import com.example.microservicio_usuario.controller.UsuarioController;
import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetUsuarioById() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");

        when(usuarioService.findById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testGetUsuarioById_NotFound() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSaveUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");

        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testGetAllUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        List<Usuario> usuarios = Collections.singletonList(usuario);

        when(usuarioService.getAll()).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioService.findById(1L)).thenReturn(usuario);
        doNothing().when(usuarioService).delete(usuario);

        mockMvc.perform(delete("/usuarios/1"))
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