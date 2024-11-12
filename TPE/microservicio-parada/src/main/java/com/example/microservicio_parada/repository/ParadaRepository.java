package com.example.microservicio_parada.repository;

import com.example.microservicio_parada.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
    // Obtener todas las Paradas habilitadas
    @Query("SELECT p FROM Parada p WHERE p.habilitado = true")
    List<Parada> findAllHabilitadas();

    // Obtener todas las Paradas deshabilitadas
    @Query("SELECT p FROM Parada p WHERE p.habilitado = false")
    List<Parada> findAllDeshabilitadas();

    // Habilitar Parada
    @Modifying
    @Transactional
    @Query("UPDATE Parada p SET p.habilitado = true WHERE p.id = :id")
    void habilitar(@Param("id") Long id);

    // Deshabilitar Parada
    @Modifying
    @Transactional
    @Query("UPDATE Parada p SET p.habilitado = false WHERE p.id = :id")
    void deshabilitar(@Param("id") Long id);

    // Obtener Monopatines
    @Query("SELECT p.idMonopatines FROM Parada p WHERE p.id = :id")
    List<Long> getIdMonopatines(Long id);
}
