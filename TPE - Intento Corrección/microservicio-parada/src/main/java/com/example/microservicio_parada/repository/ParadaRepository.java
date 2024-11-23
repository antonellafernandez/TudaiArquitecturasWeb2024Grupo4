package com.example.microservicio_parada.repository;

import com.example.microservicio_parada.entity.Parada;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends MongoRepository<Parada, String> {

    // Obtener todas las Paradas habilitadas
    List<Parada> findByHabilitadoTrue();

    // Obtener todas las Paradas deshabilitadas
    List<Parada> findByHabilitadoFalse();

    // Obtener Monopatines por ID de Parada
    List<Long> findIdMonopatinesById(String id); // Se usa String si el ID en MongoDB es String
}
