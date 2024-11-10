package com.example.microservicio_mantenimiento.repository;

import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    // Obtener Monopatin
    @Query("SELECT m.idMonopatin FROM Mantenimiento m WHERE m.id = :id")
    Long getIdMonopatin(Long id);
}
