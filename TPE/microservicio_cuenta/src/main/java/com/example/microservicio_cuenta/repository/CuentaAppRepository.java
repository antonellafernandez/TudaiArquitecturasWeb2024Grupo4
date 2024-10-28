package com.example.microservicio_cuenta.repository;

import com.example.microservicio_cuenta.entity.CuentaApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaAppRepository extends JpaRepository<CuentaApp, Long> {
}
