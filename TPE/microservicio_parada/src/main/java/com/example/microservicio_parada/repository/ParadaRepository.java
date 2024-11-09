package com.example.microservicio_parada.repository;

import com.example.microservicio_parada.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
    @Query("SELECT p.idMonopatines FROM Parada p WHERE p.id = :id")
    List<Long> getIdMonopatines(Long id);
}
