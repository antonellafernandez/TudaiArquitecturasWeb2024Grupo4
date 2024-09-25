import dtos.ReporteCarreraDTO;
import helpers.DBLoader;
import repositories.JpaCarreraRepository;
import repositories.interfaces.Repository;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import factories.RepositoryFactory;
import factories.JpaMySqlRepositoryFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //DBLoader.load();

        //Estudiante e = jpaEstudianteRepository.selectById(1);
        //e.setApellido("R");
        //jpaEstudianteRepository.save(e);

        // Generar e imprimir el reporte de carreras
        RepositoryFactory mySqlFactory = JpaMySqlRepositoryFactory.getDAOFactory(1);
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
