package helpers;

import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import factories.JpaMySqlRepositoryFactory;
import factories.RepositoryFactory;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

import java.time.LocalDate;

public class DBLoader {

    public static void load(){
        Estudiante e1 = new Estudiante("Nombre1",
                "Apellido1",
                20,
                "X",
                28563588,
                "Ciudad1",
                20L);
        Estudiante e2 = new Estudiante("Nombre2",
                "Apellido2",
                21,
                "Y",
                28563444,
                "Ciudad2",
                21L);
        Estudiante e3 = new Estudiante("Nombre3",
                "Apellido3",
                23,
                "Z",
                28555588,
                "Ciudad1",
                22L);
        Estudiante e4 = new Estudiante("Nombre4",
                "Apellido4",
                24,
                "X",
                28222588,
                "Ciudad4",
                24L);
        Estudiante e5 = new Estudiante("Nombre5",
                "Apellido5",
                25,
                "Y",
                28563998,
                "Ciudad5",
                25L);
        Estudiante e6 = new Estudiante("Nombre6",
                "Apellido6",
                26,
                "Z",
                29863588,
                "Ciudad6",
                26L);
        Estudiante e7 = new Estudiante("Nombre7",
                "Apellido7",
                27,
                "X",
                28563587,
                "Ciudad7",
                27L);
        Estudiante e8 = new Estudiante("Nombre8",
                "Apellido8",
                28,
                "X",
                28583588,
                "Ciudad2",
                28L);
        Estudiante e9 = new Estudiante("Nombre9",
                "Apellido9",
                29,
                "X",
                28963588,
                "Ciudad2",
                29L);
        Estudiante e10 = new Estudiante("Nombre10",
                "Apellido10",
                30,
                "X",
                28563580,
                "Ciudad3",
                30L);

        Carrera c1 = new Carrera("Carrera1");
        Carrera c2 = new Carrera("Carrera2");
        Carrera c4 = new Carrera("Carrera4");
        Carrera c5 = new Carrera("Carrera5");

        LocalDate ld1 = LocalDate.of(2010, 05, 03);
        LocalDate ld2 = LocalDate.of(2011, 05, 03);
        LocalDate ld3 = LocalDate.of(2012, 05, 03);
        LocalDate ld4 = LocalDate.of(2023, 05, 03);
        LocalDate ld5 = LocalDate.of(2022, 05, 03);
        LocalDate ld6 = LocalDate.of(2020, 05, 03);

        Inscripcion i1 = new Inscripcion(12, ld1, ld5, true, c1, e1);
        Inscripcion i2 = new Inscripcion(12, ld2, ld4, true, c1, e2);
        Inscripcion i3 = new Inscripcion(12, ld3, null, false, c1, e3);
        Inscripcion i4 = new Inscripcion(12, ld2, ld4, true, c2, e4);
        Inscripcion i5 = new Inscripcion(12, ld2, ld4, true, c2, e5);
        Inscripcion i6 = new Inscripcion(12, ld3, null, false, c4, e6);
        Inscripcion i7 = new Inscripcion(12, ld1, ld6, true, c4, e7);
        Inscripcion i8 = new Inscripcion(12, ld3, null, false, c4, e8);
        Inscripcion i9 = new Inscripcion(12, ld3, null, false, c4, e9);
        Inscripcion i10 = new Inscripcion(12, ld3, null, false, c5, e10);

        RepositoryFactory mySqlFactory = JpaMySqlRepositoryFactory.getDAOFactory(1);
        RepositoryEstudiante jpaEstudianteRepository = mySqlFactory.getEstudianteRepository();
        RepositoryCarrera jpaCarreraRepository = mySqlFactory.getCarreraRepository();
        RepositoryInscripcion jpaInscripcionRepository = mySqlFactory.getInscripcionRepository();

        // 2a) Dar de alta un estudiante
        jpaEstudianteRepository.save(e1);
        jpaEstudianteRepository.save(e2);
        jpaEstudianteRepository.save(e3);
        jpaEstudianteRepository.save(e4);
        jpaEstudianteRepository.save(e5);
        jpaEstudianteRepository.save(e6);
        jpaEstudianteRepository.save(e7);
        jpaEstudianteRepository.save(e8);
        jpaEstudianteRepository.save(e9);
        jpaEstudianteRepository.save(e10);

        jpaCarreraRepository.save(c1);
        jpaCarreraRepository.save(c2);
        jpaCarreraRepository.save(c4);
        jpaCarreraRepository.save(c5);

        jpaInscripcionRepository.save(i1);
        jpaInscripcionRepository.save(i2);
        jpaInscripcionRepository.save(i3);
        jpaInscripcionRepository.save(i4);
        jpaInscripcionRepository.save(i5);
        jpaInscripcionRepository.save(i6);
        jpaInscripcionRepository.save(i7);
        jpaInscripcionRepository.save(i8);
        jpaInscripcionRepository.save(i9);
        jpaInscripcionRepository.save(i10);
    }
}
