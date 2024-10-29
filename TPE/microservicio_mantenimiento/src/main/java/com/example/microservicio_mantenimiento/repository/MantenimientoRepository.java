package com.example.microservicio_mantenimiento.repository;

import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    // Puedes agregar métodos personalizados si es necesario
}