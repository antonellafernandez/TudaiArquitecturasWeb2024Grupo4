package com.example.microservicio_gps.repository;

import com.example.microservicio_gps.entity.Gps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsRepository extends JpaRepository<Gps, Long> {
}
