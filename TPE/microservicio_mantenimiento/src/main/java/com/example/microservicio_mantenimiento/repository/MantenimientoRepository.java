package com.example.microservicio_mantenimiento.repository;

import com.example.microservicio_mantenimiento.entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    // Obtener Monopatin
    @Query("SELECT m.idMonopatin FROM Mantenimiento m WHERE m.id = :id")
    Long getIdMonopatin(Long id);

    @Modifying
    @Query("UPDATE Mantenimiento m SET m.fechaFin = :fechaFin WHERE m.id = :idMantenimiento")
    Mantenimiento finalizarMantenimiento(Long idMantenimiento, LocalDateTime fechaFin);

    @Query("SELECT 1 FROM Mantenimiento m WHERE m.id = :idMantenimiento AND m.fechaFin = null")
    Boolean monopatinEnMantenimiento(Long idMantenimiento);
}
