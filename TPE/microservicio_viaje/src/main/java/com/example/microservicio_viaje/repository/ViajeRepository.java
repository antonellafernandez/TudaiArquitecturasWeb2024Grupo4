package com.example.microservicio_viaje.repository;

import com.example.microservicio_viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    List<Viaje> findByMonopatinId(Long monopatinId);

    Viaje findViajeByMonopatinId(Long monopatinId);
}
