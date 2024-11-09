package com.example.microservicio_usuario.service;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.feignClients.CuentaAppFeignClient;
import com.example.microservicio_usuario.feignClients.MonopatinFeignClient;
import com.example.microservicio_usuario.models.CuentaApp;
import com.example.microservicio_usuario.models.Monopatin;
import com.example.microservicio_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CuentaAppFeignClient cuentaAppFeignClient;

    @Autowired
    MonopatinFeignClient monopatinFeignClient;

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<CuentaApp> getCuentaAppsById(Long id) {
        List<CuentaApp> salida = new ArrayList<CuentaApp>();
        List<Long> idCuentaApps = usuarioRepository.getIdCuentaApps(id);

        for (Long idCuenta : idCuentaApps) {
            salida.add(cuentaAppFeignClient.getCuentaById(idCuenta));
        }

        return salida;
    }

    public Monopatin getMonopatinById(Long id) {
        return monopatinFeignClient.getMonopatinById(id);
    }
}
