import dtos.CarreraConCantInscriptosDTO;
import dtos.EstudianteDTO;
import dtos.ReporteCarreraDTO;
import entities.Estudiante;
<<<<<<< HEAD
import helpers.DBLoader;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
=======
import entities.Inscripcion;
import helpers.DBLoader;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.JpaInscripcionRepository;
>>>>>>> 2302d6d3220996f16e7450764d8b77a152bf5811
import repositories.interfaces.Repository;
import entities.Carrera;
import factories.RepositoryFactory;
import factories.JpaMySqlRepositoryFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //DBLoader.load();

        RepositoryFactory mySqlFactory = JpaMySqlRepositoryFactory.getDAOFactory(1);
        //2.C
        Repository<Estudiante> jpaEstudianteRepository = mySqlFactory.getEstudianteRepository();
        JpaEstudianteRepository repoEstudiante = (JpaEstudianteRepository) jpaEstudianteRepository;
        List<EstudianteDTO> estudiantesOrdenadosPorNombre = repoEstudiante.obtenerEstudiantesOrdenadosPorNombre();

<<<<<<< HEAD
        // 3) Generar e imprimir el reporte de carreras
        Repository<Carrera> jpaCarreraRepository = mySqlFactory.getCarreraRepository();
        JpaCarreraRepository repoCarrera = (JpaCarreraRepository) jpaCarreraRepository;

        List<ReporteCarreraDTO> reporte = repoCarrera.generarReporteCarreras();
=======
        //2.D
        EstudianteDTO estudiantePorLu = repoEstudiante.obtenerEstudiantePorLu(28L);

        //2.E
        List<EstudianteDTO> estudiantesPorGenero = repoEstudiante.obtenerEstudiantesPorGenero("X");

        //2.F
        Repository<Inscripcion> jpaInscripcionRepository = mySqlFactory.getInscripcionRepository();
        JpaInscripcionRepository repoInscriptos = (JpaInscripcionRepository) jpaInscripcionRepository;
        List<CarreraConCantInscriptosDTO> listCarrerasConCantInscriptos = repoInscriptos.recuperarCarrerasOrdenadasPorCantidadInscriptos();

        //2.G
        Repository<Carrera> jpaCarreraRepository = mySqlFactory.getCarreraRepository();
        JpaCarreraRepository repoCarrera = (JpaCarreraRepository) jpaCarreraRepository;
        Carrera carreraElegida = repoCarrera.selectById(12);
        String ciudadElegida = "Ciudad2";
        List<EstudianteDTO> listEstudiantesDeCarreraPorCiudad = repoInscriptos.recuperarEstudiantesPorCarreraYCiudad(carreraElegida, ciudadElegida);
>>>>>>> 2302d6d3220996f16e7450764d8b77a152bf5811

        //3.
        List<ReporteCarreraDTO> reporteCarreraDTO = repoCarrera.generarReporteCarreras();


        //2.C.recuperar todos los estudiantes por nombre
        System.out.println("------- Estudiantes ordenados por nombre -------");
        for(EstudianteDTO estudianteDTO : estudiantesOrdenadosPorNombre) {
            System.out.println(estudianteDTO);
        }
        System.out.println("------------------------------------------------");


        //2.D.recuperar un estudiante, en base a su número de libreta universitaria.
        System.out.println("------- Estudiante obtenido por lu -------");
        System.out.println("Estudiante obtenido por LU = 28:" + estudiantePorLu);
        System.out.println("------------------------------------------------");

        //2.E  recuperar todos los estudiantes, en base a su género.
        System.out.println("------- Estudiantes obtenidos por genero -------");
        for(EstudianteDTO estudianteDTO : estudiantesPorGenero) {
            System.out.println(estudianteDTO);
        }
        System.out.println("------------------------------------------------");

        //2.F. recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos

        System.out.println("------- Carreras obtenidas con estudiantes inscriptos y ordenadas por la cantidad de ellos -------");
        for(CarreraConCantInscriptosDTO carrera : listCarrerasConCantInscriptos) {
            System.out.println(carrera);
        }
        System.out.println("------------------------------------------------");

        //2.G. recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        System.out.println("------- Estudiantes obtenidos por carrera y ciudad -------");
        for(EstudianteDTO estudiante : listEstudiantesDeCarreraPorCiudad) {
            System.out.println(estudiante); //Por algun extraño motivo se imprime antes que los sout planos
        }
        System.out.println("------------------------------------------------");

        //3. Generar e imprimir el reporte de carreras
        if (reporteCarreraDTO != null) {
            System.out.println("===============================================================================================================================" +
                    " Reporte de Carreras " +
                    "===============================================================================================================================");
            for (ReporteCarreraDTO dto : reporteCarreraDTO) {
                System.out.println(dto);
            }
        } else {
            System.out.println("No se pudo generar el reporte.");
        }

        // Cerrar los EntityManager
        repoCarrera.close();
    }
}
