package com.example.microservicio_monopatin.repository;

import com.example.microservicio_monopatin.dtos.ReporteUsoDto;
import com.example.microservicio_monopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

    @Modifying
    @Query("UPDATE Monopatin m SET m.disponible = true WHERE m.id = :monopatinId")
    Integer habilitar(Long monopatinId);

    @Modifying
    @Query("UPDATE Monopatin m SET m.disponible = false WHERE m.id = :monopatinId")
    Integer deshabilitar(Long monopatinId);

    @Query("SELECT new com.example.microservicio_monopatin.dtos.ReporteUsoDto(m.id, m.kmRecorridosTotales, null) " +
            "FROM Monopatin m ")
    List<ReporteUsoDto> reporteUsoPorKilometro();

    @Query("SELECT new com.example.microservicio_monopatin.dtos.ReporteUsoDto(m.id, null, m.tiempoRecorridosTotales) " +
            "FROM Monopatin m ")
    List<ReporteUsoDto>reporteUsoPorTiempo();

    @Query("SELECT new com.example.microservicio_monopatin.dtos.ReporteUsoDto(m.id, m.kmRecorridosTotales, m.tiempoRecorridosTotales) " +
            "FROM Monopatin m " +
            "WHERE m.disponible OR (m.disponible = false AND m.idViajeActivo != null)")
    List<ReporteUsoDto>reporteUsoCompleto();



}