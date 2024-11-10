package com.example.microservicio_administrador.repository;

import com.example.microservicio_administrador.dto.TarifaDto;
import com.example.microservicio_administrador.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query("SELECT t.nombreTarifa, t.tipoTarifa, t.precioTarifa, t.descuentoTarifa " +
            "FROM Tarifa t " +
            "WHERE t.tipoTarifa = :tipo")
    public Optional<Tarifa> getTarifaByTipo(String tipo);


}