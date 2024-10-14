package com.example.tp_03_ejercicio_integrador;

import com.example.tp_03_ejercicio_integrador.utils.CargaDeDatos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.tp_03_ejercicio_integrador.repositorios")
public class Tp03EjercicioIntegradorApplication {
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(Tp03EjercicioIntegradorApplication.class, args);

		// Obtener el bean 'CargaDeDatos' gestionado por Spring
		CargaDeDatos cargaDeDatos = context.getBean(CargaDeDatos.class);

		// Cargar los datos
		cargaDeDatos.cargarDatosDesdeCSV();
	}
}
