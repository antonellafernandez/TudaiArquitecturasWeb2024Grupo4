package com.example.tp_03_ejercicio_integrador.utils;

import com.example.tp_03_ejercicio_integrador.model.Carrera;
import com.example.tp_03_ejercicio_integrador.model.Estudiante;
import com.example.tp_03_ejercicio_integrador.model.EstudianteCarrera;

import com.example.tp_03_ejercicio_integrador.repository.RepoCarrera;
import com.example.tp_03_ejercicio_integrador.repository.RepoEstudiante;
import com.example.tp_03_ejercicio_integrador.repository.RepoEstudianteCarrera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CargaDeDatos {

    private final RepoCarrera rc;
    private final RepoEstudiante re;
    private final RepoEstudianteCarrera rec;

    @Autowired
    public CargaDeDatos(RepoCarrera rc, RepoEstudiante re, RepoEstudianteCarrera rec) {
        this.rc = rc;
        this.re = re;
        this.rec = rec;
    }

    public void cargarDatosDesdeCSV() throws IOException {
        File carrerasCSV = ResourceUtils.getFile("src/main/java/com.example.tp_03_ejercicio_integrador/csv/carreras.csv");
        File estudiantesCSV = ResourceUtils.getFile("src/main/java/com.example.tp_03_ejercicio_integrador/csv/estudiantes.csv");
        File estudianteCarreraCSV = ResourceUtils.getFile("src/main/java/com.example.tp_03_ejercicio_integrador/csv/estudianteCarrera.csv");

        try (FileReader readerCarreras = new FileReader(carrerasCSV);
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerCarreras)) {

            for (CSVRecord csvRecord : csvParser) {
                Carrera carrera = new Carrera(Integer.parseInt(csvRecord.get("id_carrera")), csvRecord.get("nombre"));
                rc.save(carrera); // Guardar carrera en la base de datos
            }
        }

        try (FileReader readerEstudiantes = new FileReader(estudiantesCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerEstudiantes)) {

            for (CSVRecord csvRecord : csvParser) {
                Estudiante estudiante = new Estudiante(Integer.parseInt(csvRecord.get("DNI")), csvRecord.get("nombre"),
                        csvRecord.get("apellido"), Integer.parseInt(csvRecord.get("edad")), csvRecord.get("genero"),
                        csvRecord.get("ciudad"), Long.parseLong(csvRecord.get("LU")));
                re.save(estudiante); // Guardar estudiante en la base de datos
            }
        }

        try (FileReader readerEstudianteCarrera = new FileReader(estudiantesCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerEstudianteCarrera)) {

            for (CSVRecord csvRecord : csvParser) { // PREGUNTAR en lugar de id_estudiante debería recibir Estudiante
                EstudianteCarrera estudianteCarrera = new EstudianteCarrera(Integer.parseInt(csvRecord.get("id")),
                        Integer.parseInt(csvRecord.get("id_estudiante")), Integer.parseInt(csvRecord.get("id_carrera")),
                        Integer.parseInt(csvRecord.get("anioInscripcion")), Integer.parseInt(csvRecord.get("anioEgreso")),
                        Integer.parseInt(csvRecord.get("antiguedad")), Boolean.parseBoolean(csvRecord.get("graduado")));
                rec.save(estudianteCarrera); // Guardar inscripción en la base de datos
            }
        }
    }

}
