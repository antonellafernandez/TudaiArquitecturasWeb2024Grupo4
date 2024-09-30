import dtos.ReporteCarreraDTO;
import entities.Estudiante;
import helpers.DBLoader;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.interfaces.Repository;
import entities.Carrera;
import factories.RepositoryFactory;
import factories.JpaMySqlRepositoryFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        DBLoader.load();

        RepositoryFactory mySqlFactory = JpaMySqlRepositoryFactory.getDAOFactory(1);

        // 3) Generar e imprimir el reporte de carreras
        Repository<Carrera> jpaCarreraRepository = mySqlFactory.getCarreraRepository();
        JpaCarreraRepository repoCarrera = (JpaCarreraRepository) jpaCarreraRepository;

        List<ReporteCarreraDTO> reporte = repoCarrera.generarReporteCarreras();

        if (reporte != null) {
            System.out.println("===============================================================================================================================" +
                    " Reporte de Carreras " +
                    "===============================================================================================================================");
            for (ReporteCarreraDTO dto : reporte) {
                System.out.println(dto);
            }
        } else {
            System.out.println("No se pudo generar el reporte.");
        }

        // Cerrar los EntityManager
        repoCarrera.close();
    }
}
