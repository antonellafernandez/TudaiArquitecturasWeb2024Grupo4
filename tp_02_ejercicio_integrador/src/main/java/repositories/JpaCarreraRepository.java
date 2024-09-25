package repositories;

import repositories.interfaces.Repository;
import dtos.ReporteCarreraDTO;
import entities.Carrera;
import entities.Inscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaCarreraRepository implements Repository<Carrera> {
    private EntityManager em;
    private static JpaCarreraRepository instance;

    private JpaCarreraRepository(EntityManager em) {
        this.em = em;
    }

    public static JpaCarreraRepository getInstance(EntityManager em) {
        if(instance == null)
            instance = new JpaCarreraRepository(em);
        return instance;
    }

    // Al tener cascade = CascadeType.ALL, cualquier operación realizada en la entidad Carrera
    // (insertar, actualizar, eliminar) también afectará automáticamente a las entidades relacionadas
    @Override
    public void save(Carrera carrera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if(carrera.getId() == 0){
            try {
                em.persist(carrera);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar carrera! " + e.getMessage());
                throw e;
            }
        }
        else{
            try {
                em.merge(carrera);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al actualizar carrera! " + e.getMessage());
                throw e;
            }
        }
    }

    @Override
    public Carrera selectById(int id) {
        try {
            TypedQuery<Carrera> query = em.createQuery("SELECT c FROM Carrera c WHERE c.id = :id", Carrera.class);
            query.setParameter("id", id);
            System.out.println(query.getSingleResult());
            return query.getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carrera por id! " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Carrera> selectAll() {
        try {
            return em.createQuery("SELECT c FROM Carrera c", Carrera.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carreras! " + e.getMessage());
            throw e;
        }
    }

    // Al tener cascade = CascadeType.ALL, cualquier operación realizada en la entidad Carrera
    // (insertar, actualizar, eliminar) también afectará automáticamente a las entidades relacionadas
    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Carrera carrera = em.find(Carrera.class, id);

            if (carrera != null) {
                em.remove(carrera);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                System.out.println("La carrera con id=" + id + " no existe!");
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al eliminar carrera! " + e.getMessage());
            return false;
        }
    }

    public List<ReporteCarreraDTO> generarReporteCarreras() {
        try {
            String jpql = "SELECT new dtos.ReporteCarreraDTO(c.nombre, " +
                    "YEAR(i.anioInscripcion), " +
                    "YEAR(i.anioEgreso), " +
                    "(SELECT COUNT(i1) FROM Inscripcion i1 WHERE i1.anioEgreso IS NULL AND i1.carrera = c), " + // Inscripciones sin egreso
                    // *Segun chatGPT no se podia hacer el count con el case*
                    "(SELECT COUNT(i2) FROM Inscripcion i2 WHERE i2.anioEgreso IS NOT NULL AND i2.carrera = c), " + // Inscripciones con egreso
                    "e.id) " +
                    "FROM Inscripcion i " +
                    "JOIN i.carrera c " +
                    "JOIN i.estudiante e " +
                    //"GROUP BY c.nombre, YEAR(i.anioInscripcion), YEAR(i.anioEgreso), e.id " +
                    "ORDER BY c.nombre ASC, YEAR(i.anioInscripcion) ASC, YEAR(i.anioEgreso) ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        }
    }
}
