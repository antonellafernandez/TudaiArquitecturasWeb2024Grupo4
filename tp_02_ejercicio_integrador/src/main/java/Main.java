import dtos.CarreraConCantInscriptosDTO;
import dtos.ReporteCarreraDTO;
import helpers.DBLoader;
import repositories.JpaCarreraRepository;
import repositories.JpaInscripcionRepository;
import repositories.interfaces.Repository;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import factories.RepositoryFactory;
import factories.JpaMySqlRepositoryFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        DBLoader.load();

        //Estudiante e = jpaEstudianteRepository.selectById(1);
        //e.setApellido("R");
        //jpaEstudianteRepository.save(e);

        RepositoryFactory mySqlFactory = JpaMySqlRepositoryFactory.getDAOFactory(1);
        //Obtener carreras ordenadas por la cantidad de inscriptos
        /*Repository<Inscripcion> jpaInscripcionRepository = mySqlFactory.getInscripcionRepository();
        JpaInscripcionRepository repoInscripcion = (JpaInscripcionRepository) jpaInscripcionRepository;
        List<CarreraConCantInscriptosDTO> reporte = repoInscripcion.recuperarCarrerasOrdenadasPorCantidadInscriptos();
        if (reporte != null) {
            for(CarreraConCantInscriptosDTO dto : reporte) {
                System.out.println(dto);
            }
        } else {
            System.out.println("No se pudo generar el reporte.");
        }*/

        // Generar e imprimir el reporte de carreras
        Repository<Carrera> jpaCarreraRepository = mySqlFactory.getCarreraRepository();
        JpaCarreraRepository repoCarrera = (JpaCarreraRepository) jpaCarreraRepository;
        List<ReporteCarreraDTO> reporte = repoCarrera.generarReporteCarreras();
        if (reporte != null) {
            for (ReporteCarreraDTO dto : reporte) {
                System.out.println(dto);
            }
        } else {
            System.out.println("No se pudo generar el reporte.");
        }
    }
}
