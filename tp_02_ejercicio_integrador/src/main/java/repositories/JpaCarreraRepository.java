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

    public JpaCarreraRepository(EntityManager em) {
        this.em = em;
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
                    "i.anioInscripcion, " +
                    "i.anioEgreso, " +
                    "COUNT(CASE WHEN i.anioEgreso IS NULL THEN 1 END), " + // Inscripciones sin egreso
                    "COUNT(CASE WHEN i.anioEgreso IS NOT NULL THEN 1 END)) " + // Inscripciones con egreso
                    "FROM Inscripcion i " +
                    "JOIN i.carrera c " +
                    "GROUP BY c.nombre, i.anioInscripcion, i.anioEgreso " +
                    "ORDER BY c.nombre ASC, i.anioInscripcion ASC, i.anioEgreso ASC";

            TypedQuery<ReporteCarreraDTO> query = em.createQuery(jpql, ReporteCarreraDTO.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        }
    }
}
