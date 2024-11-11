package com.example.microservicio_monopatin.repository;

import com.example.microservicio_monopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
    Monopatin findMonopatinByViajeId(Long viajeId);

    @Modifying
    @Query("UPDATE Monopatin m SET m.disponible = true WHERE m.id = :monopatinId")
    Monopatin habilitar(Long monopatinId);

    @Modifying
    @Query("UPDATE Monopatin m SET m.disponible = false WHERE m.id = :monopatinId")
    Monopatin deshabilitar(Long monopatinId);
}