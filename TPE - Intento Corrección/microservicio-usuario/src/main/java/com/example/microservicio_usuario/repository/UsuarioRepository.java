package com.example.microservicio_usuario.repository;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.models.CuentaApp;
import com.example.microservicio_usuario.models.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Obtener todos los Usuarios habilitados
    @Query("SELECT u FROM Usuario u WHERE u.habilitado = true")
    List<Usuario> findAllHabilitados();

    // Obtener todas los Usuarios deshabilitados
    @Query("SELECT u FROM Usuario u WHERE u.habilitado = false")
    List<Usuario> findAllDeshabilitados();

    // Habilitar Usuario
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.habilitado = true WHERE u.id = :id")
    void habilitar(@Param("id") Long id);

    // Deshabilitar Usuario
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.habilitado = false WHERE u.id = :id")
    void deshabilitar(@Param("id") Long id);

    // Obtener CuentaApps
    @Query("SELECT u.idCuentaApps FROM Usuario u WHERE u.id = :id")
    List<Long> getIdCuentaApps(Long id);

    // Obtener Monopatin
    @Query("SELECT u.idMonopatinActual FROM Usuario u WHERE u.id = :id")
    Monopatin getIdMonopatin(Long id);

    @Query("SELECT u.username FROM Usuario u WHERE u.username = :username")
    Usuario findByUsername(String username);
}
