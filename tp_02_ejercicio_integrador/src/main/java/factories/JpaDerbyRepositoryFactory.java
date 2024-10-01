package factories;

import repositories.interfaces.RepositoryCarrera;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.JpaInscripcionRepository;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaDerbyRepositoryFactory extends RepositoryFactory {
    private static final String PERSISTENCE_UNIT_NAME = "Integrador 2";

    @Override
    public RepositoryCarrera getCarreraRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return JpaCarreraRepository.getInstance(em);
    }

    @Override
    public RepositoryEstudiante getEstudianteRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return JpaEstudianteRepository.getInstance(em);
    }

    @Override
    public RepositoryInscripcion getInscripcionRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return JpaInscripcionRepository.getInstance(em);
    }
}
