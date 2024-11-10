package com.example.microservicio_administrador.service;

import com.example.microservicio_administrador.dto.TarifaDto;
import com.example.microservicio_administrador.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaService {

    @Autowired
    TarifaRepository tarifaRepository;

    public TarifaDto getTarifaByTipo(String tipo) {
        return tarifaRepository.getTarifaByTipo(tipo)
                .map(TarifaDto::new)
                .orElseThrow(()-> new RuntimeException("No se encontro el tipo de tarifa: " + tipo));
    }

    public List<TarifaDto> getTarifas() {
        return tarifaRepository.findAll().stream()
                .map(TarifaDto::new)
                .toList();
    }


}
