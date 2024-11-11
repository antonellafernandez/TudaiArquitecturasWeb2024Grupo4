package com.example.microservicio_administrador.service;

import com.example.microservicio_administrador.dto.TarifaDto;
import com.example.microservicio_administrador.entity.Tarifa;
import com.example.microservicio_administrador.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarifaService {

    @Autowired
    TarifaRepository tarifaRepository;

    @Transactional(readOnly = true)
    public TarifaDto getTarifaByTipo(String tipo) {
        return tarifaRepository.getTarifaByTipo(tipo)
                .map(TarifaDto::new)
                .orElseThrow(()-> new RuntimeException("No se encontro el tipo de tarifa: " + tipo));
    }

    @Transactional(readOnly = true)
    public List<TarifaDto> getTarifas() {
        try{
            return tarifaRepository.findAll().stream()
                    .map(TarifaDto::new)
                    .toList();
        }catch(Exception e){
            throw new RuntimeException("No se encontraron tarifas");
        }
    }

    @Transactional
    public TarifaDto save(TarifaDto tarifaDto) {
        try {
            Tarifa tarifaModificada = new Tarifa(tarifaDto.getNombreTarifa(), tarifaDto.getTipoTarifa(), tarifaDto.getPrecioTarifa(), tarifaDto.getDescuentoTarifa());

            tarifaRepository.save(tarifaModificada);
            return tarifaDto;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar carrera!" + e.getMessage());
        }
    }

    @Transactional
    public TarifaDto update(Long id, TarifaDto tarifaDto) {
        try {
            Tarifa tarifaModificada = new Tarifa(tarifaDto.getNombreTarifa(), tarifaDto.getTipoTarifa(), tarifaDto.getPrecioTarifa(), tarifaDto.getDescuentoTarifa());

            if (tarifaRepository.existsById(id)){
                tarifaRepository.save(tarifaModificada);
                return tarifaDto;
            }
            throw new RuntimeException("No existe un tarifa con id=" + id + "!");
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar tarifa con id=" + id + "!" + e.getMessage());
        }
    }

    public boolean delete(Long id) {
        try{
            tarifaRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar tarifa con id=" + id + "!" + e.getMessage());
        }
    }

}