package com.example.microserviciocuentaapp.repository;

import com.example.microserviciocuentaapp.entity.CuentaApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaAppRepository extends JpaRepository<CuentaApp, Long> {
}
