package com.example.microservicio_usuario;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.repository.UsuarioRepository;
import com.example.microservicio_usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan.perez@example.com");
        usuario.setHabilitado(true);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario savedUsuario = usuarioService.save(usuario);

        assertNotNull(savedUsuario);
        assertEquals("Juan", savedUsuario.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testGetAllUsuarios() {
        List<Usuario> list = new ArrayList<>();
        list.add(new Usuario());

        when(usuarioRepository.findAll()).thenReturn(list);

        List<Usuario> usuarios = usuarioService.getAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testFindUsuarioById() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario foundUsuario = usuarioService.findById(1L);
        assertNotNull(foundUsuario);
        assertEquals(1L, foundUsuario.getId());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.delete(usuario);
        verify(usuarioRepository, times(1)).delete(usuario);
    }


}