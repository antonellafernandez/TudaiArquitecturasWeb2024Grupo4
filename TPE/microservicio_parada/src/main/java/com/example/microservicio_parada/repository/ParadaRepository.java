package com.example.microservicio_parada.repository;

import com.example.microservicio_parada.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
}