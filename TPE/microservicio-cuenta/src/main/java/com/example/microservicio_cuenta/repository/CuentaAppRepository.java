package com.example.microservicio_cuenta.repository;

import com.example.microservicio_cuenta.entity.CuentaApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CuentaAppRepository extends JpaRepository<CuentaApp, Long> {
    // Obtener todas las Cuentas habilitadas
    @Query("SELECT c FROM CuentaApp c WHERE c.habilitado = true")
    List<CuentaApp> findAllHabilitadas();

    // Obtener todas las Cuentas deshabilitadas
    @Query("SELECT c FROM CuentaApp c WHERE c.habilitado = false")
    List<CuentaApp> findAllDeshabilitadas();

    // Habilitar Cuenta
    @Modifying
    @Transactional
    @Query("UPDATE CuentaApp c SET c.habilitado = true WHERE c.id = :id")
    void habilitar(@Param("id") Long id);

    // Deshabilitar Cuenta
    @Modifying
    @Transactional
    @Query("UPDATE CuentaApp c SET c.habilitado = false WHERE c.id = :id")
    void deshabilitar(@Param("id") Long id);

    // Obtener Usuarios
    @Query("SELECT c.idUsuarios FROM CuentaApp c WHERE c.id = :id")
    List<Long> getIdUsuarios(Long id);
}
