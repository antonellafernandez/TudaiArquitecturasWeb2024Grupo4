package daos;

import daos.interfaces.DAO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class JpaInscripcionDAO implements DAO<Inscripcion> {
    private EntityManager em;

    public JpaInscripcionDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void insert(Inscripcion inscripcion) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(inscripcion);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al insertar inscripción! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Inscripcion selectById(int id) {
        try {
            return em.createQuery("SELECT i FROM Inscripcion i WHERE i.id = :id", Inscripcion.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener inscripcion por id! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Inscripcion> selectAll() {
        try {
            return em.createQuery("SELECT i FROM Inscripcion i", Inscripcion.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener inscripciones! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(Inscripcion inscripcion) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    // b) Matricular un estudiante en una carrera
    public void matricularEstudianteEnCarrera(Estudiante estudiante, Carrera carrera, int antiguedad, boolean graduado) {
        Inscripcion inscripcion = new Inscripcion(antiguedad, graduado, carrera, estudiante);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(inscripcion);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al matricular estudiante! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    // g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
    public List<Estudiante> recuperarEstudiantesPorCarreraYCiudad(Carrera carrera, String ciudadResidencia) {
        try {
            return em.createQuery(
                            "SELECT e FROM Inscripcion i JOIN i.estudiante e WHERE i.carrera = :carrera AND e.ciudadResidencia = :ciudad", Estudiante.class)
                    .setParameter("carrera", carrera)
                    .setParameter("ciudad", ciudadResidencia)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiantes por carrera y residencia! " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
}
