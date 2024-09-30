import dtos.CarreraConCantInscriptosDTO;
import dtos.EstudianteDTO;
import dtos.ReporteCarreraDTO;
import entities.Estudiante;
import helpers.DBLoader;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import entities.Inscripcion;
import helpers.DBLoader;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.JpaInscripcionRepository;
import repositories.interfaces.Repository;
import entities.Carrera;
import factories.RepositoryFactory;
import factories.JpaMySqlRepositoryFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // DBLoader.load();

        RepositoryFactory mySqlFactory = JpaMySqlRepositoryFactory.getDAOFactory(1);

        // 2c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple -> Por nombre
        Repository<Estudiante> jpaEstudianteRepository = mySqlFactory.getEstudianteRepository();
        JpaEstudianteRepository repoEstudiante = (JpaEstudianteRepository) jpaEstudianteRepository;
        List<EstudianteDTO> estudiantesOrdenadosPorNombre = repoEstudiante.obtenerEstudiantesOrdenadosPorNombre();

        System.out.println("===============================================================================================================================" +
                " Estudiantes ordenados por nombre " +
                "===============================================================================================================================");

        for(EstudianteDTO estudianteDTO : estudiantesOrdenadosPorNombre) {
            System.out.println(estudianteDTO);
        }

        System.out.println();

        // 2d) Recuperar un estudiante en base a su número de libreta universitaria
        EstudianteDTO estudiantePorLu = repoEstudiante.obtenerEstudiantePorLu(28L);

        System.out.println("===============================================================================================================================" +
                " Estudiante obtenido por lu " +
                "===============================================================================================================================");
        System.out.println("Estudiante obtenido por LU = 28:" + estudiantePorLu);

        System.out.println();

        // 2e) Recuperar todos los estudiantes en base a su género
        List<EstudianteDTO> estudiantesPorGenero = repoEstudiante.obtenerEstudiantesPorGenero("X");

        System.out.println("===============================================================================================================================" +
                " Estudiantes obtenidos por género " +
                "===============================================================================================================================");

        for (EstudianteDTO estudianteDTO : estudiantesPorGenero) {
            System.out.println(estudianteDTO);
        }

        System.out.println();

        // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
        Repository<Inscripcion> jpaInscripcionRepository = mySqlFactory.getInscripcionRepository();
        JpaInscripcionRepository repoInscriptos = (JpaInscripcionRepository) jpaInscripcionRepository;
        List<CarreraConCantInscriptosDTO> listCarrerasConCantInscriptos = repoInscriptos.recuperarCarrerasOrdenadasPorCantidadInscriptos();

        System.out.println("===============================================================================================================================" +
                " Carreras obtenidas con estudiantes inscriptos y ordenadas por la cantidad de ellos " +
                "===============================================================================================================================");

        for (CarreraConCantInscriptosDTO carrera : listCarrerasConCantInscriptos) {
            System.out.println(carrera);
        }

        System.out.println();

        // 2g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
        Repository<Carrera> jpaCarreraRepository = mySqlFactory.getCarreraRepository();
        JpaCarreraRepository repoCarrera = (JpaCarreraRepository) jpaCarreraRepository;
        Carrera carreraElegida = repoCarrera.selectById(12);
        String ciudadElegida = "Ciudad2";
        List<EstudianteDTO> listEstudiantesDeCarreraPorCiudad = repoInscriptos.recuperarEstudiantesPorCarreraYCiudad(carreraElegida, ciudadElegida);

        System.out.println("===============================================================================================================================" +
                " Estudiantes obtenidos por carrera y ciudad " +
                "===============================================================================================================================");

        for (EstudianteDTO estudiante : listEstudiantesDeCarreraPorCiudad) {
            System.out.println(estudiante); //Por algun extraño motivo se imprime antes que los sout planos
        }

        System.out.println();

        // 3) Generar un reporte de las carreras, que para cada carrera incluya información de los
        // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
        // los años de manera cronológica
        List<ReporteCarreraDTO> reporteCarreraDTO = repoCarrera.generarReporteCarreras();

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
        repoEstudiante.close();
        repoInscriptos.close();
    }
}
