package com.example.microservicio_viaje.repository;

import com.example.microservicio_viaje.dto.ReporteUsoPorTiempoDto;
import com.example.microservicio_viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("SELECT 1 FROM Viaje v WHERE v.idMonopatin = :idMonopatin")
    Boolean monopatinTieneViajes(Long idMonopatin);

    @Query("SELECT new com.example.microservicio_viaje.dto.ReporteUsoPorTiempoDto(v.idMonopatin, v.inicioPausasFinal) " +
            "FROM Viaje v ")
    List<ReporteUsoPorTiempoDto>reporteUsoPorTiempo();
}
