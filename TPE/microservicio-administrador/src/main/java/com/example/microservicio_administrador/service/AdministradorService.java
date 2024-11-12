package com.example.microservicio_administrador.service;

import com.example.microservicio_administrador.dto.AdministradorDto;
import com.example.microservicio_administrador.entity.Administrador;
import com.example.microservicio_administrador.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Transactional(readOnly = true)
    public List<AdministradorDto> getAllAdministradores() {

        return administradorRepository.findAll()
                .stream()
                .map(AdministradorDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdministradorDto getAdministradorById(Long id) {

        return administradorRepository.findById(id)
                .map(AdministradorDto::new)
                .orElseThrow(()-> new RuntimeException("No se encontro el administrador"));
    }

    @Transactional
    public AdministradorDto save(AdministradorDto newAdmin) {
        Administrador admin = new Administrador();
        admin.setNombre(newAdmin.getNombre());

        if(administradorRepository.save(admin) != null)
            return newAdmin;

        return null;
    }

    @Transactional
    public AdministradorDto delete(Long id) {
        if(administradorRepository.existsById(id))
            administradorRepository.deleteById(id);

        return null;
    }
}
