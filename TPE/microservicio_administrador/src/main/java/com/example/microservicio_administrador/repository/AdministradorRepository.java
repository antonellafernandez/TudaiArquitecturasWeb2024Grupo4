package com.example.microservicio_administrador.repository;

import com.example.microservicio_administrador.dto.AdministradorResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorResponseDto, Long> {

}
