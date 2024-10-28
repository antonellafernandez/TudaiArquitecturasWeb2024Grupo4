package com.example.microserviciomonopatin.repository;

import com.example.microserviciomonopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
}
