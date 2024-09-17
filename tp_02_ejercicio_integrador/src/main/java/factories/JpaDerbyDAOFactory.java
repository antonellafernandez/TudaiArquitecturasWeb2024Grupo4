package factories;

import daos.interfaces.DAO;
import daos.JpaCarreraDAO;
import daos.JpaEstudianteDAO;
import daos.JpaInscripcionDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaDerbyDAOFactory extends DAOFactory {
    private static final String PERSISTENCE_UNIT_NAME = "Example";

    @Override
    public DAO getCarreraDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaCarreraDAO(em);
    }

    @Override
    public DAO getEstudianteDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaEstudianteDAO(em);
    }

    @Override
    public DAO getInscripcionDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        return new JpaInscripcionDAO(em);
    }
}
