package com.example.microservicio_administrador.service;

import com.example.microservicio_administrador.dto.AdministradorDto;
import com.example.microservicio_administrador.entity.Administrador;
import com.example.microservicio_administrador.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {
    @Autowired
    private AdministradorRepository administradorRepository;

    public List<AdministradorDto> getAllAdministradores() {

        return administradorRepository.findAll()
                .stream()
                .map(AdministradorDto::new)
                .toList();
    }

    public AdministradorDto getAdministradorById(Long id) {

        return administradorRepository.findById(id)
                .map(AdministradorDto::new)
                .orElseThrow(()-> new RuntimeException("No se encontro el administrador"));
    }

    public AdministradorDto save(AdministradorDto newAdmin) {
        Administrador admin = new Administrador();
        admin.setNombre(newAdmin.getNombre());

        if(administradorRepository.save(admin) != null)
            return newAdmin;

        return null;
    }

    public AdministradorDto delete(Long id) {
        if(administradorRepository.existsById(id))
            administradorRepository.deleteById(id);

        return null;
    }

}
