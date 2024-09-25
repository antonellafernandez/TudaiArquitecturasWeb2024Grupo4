package factories;

import repositories.interfaces.Repository;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.JpaInscripcionRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMySqlRepositoryFactory extends RepositoryFactory {
    private static final String PERSISTENCE_UNIT_NAME = "Example";

    @Override
    public Repository getCarreraDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaCarreraRepository(em);
    }

    @Override
    public Repository getEstudianteDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaEstudianteRepository(em);
    }

    @Override
    public Repository getInscripcionDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaInscripcionRepository(em);
    }
}
