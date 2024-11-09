package com.example.microservicio_usuario.repository;

import com.example.microservicio_usuario.entity.Usuario;
import com.example.microservicio_usuario.models.CuentaApp;
import com.example.microservicio_usuario.models.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u.idCuentaApps FROM Usuario u WHERE u.id = :id")
    List<Long> getIdCuentaApps(Long id);

    @Query("SELECT u.idMonopatinActual FROM Usuario u WHERE u.id = :id")
    Monopatin getIdMonopatin(Long id);
}
