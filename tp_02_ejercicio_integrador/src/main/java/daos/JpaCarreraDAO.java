package daos;

import daos.interfaces.DAO;
import entities.Carrera;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class JpaCarreraDAO implements DAO<Carrera> {
    private EntityManager em;

    public JpaCarreraDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void insert(Carrera carrera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(carrera);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al insertar carrera! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Carrera selectById(int id) {
        try {
            return em.createQuery("SELECT c FROM Carrera c WHERE c.id = :id", Carrera.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carrera por id! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Carrera> selectAll() {
        try {
            return em.createQuery("SELECT c FROM Carrera c", Carrera.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carreras! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(Carrera carrera) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
