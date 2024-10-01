package repositories;

import dtos.CarreraDTO;
import repositories.interfaces.RepositoryCarrera;
import dtos.ReporteCarreraDTO;
import entities.Carrera;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

// Singleton
public class JpaCarreraRepository implements RepositoryCarrera {
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

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
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
        } else { // Si la carrera ya existe, hace update
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
    public CarreraDTO selectById(int id) {
        try {
            TypedQuery<CarreraDTO> query = em.createQuery(
                    "SELECT new dtos.CarreraDTO(c.nombre) " +
                            "FROM Carrera c " +
                            "WHERE c.id = :id"
                    , CarreraDTO.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carrera por id! " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<CarreraDTO> selectAll() {
        try {
            return em.createQuery(
                    "SELECT new dtos.CarreraDTO(c.nombre) " +
                            "FROM Carrera c"
                    , CarreraDTO.class).getResultList();
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

    // 3) Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
    // los años de manera cronológica
    public List<ReporteCarreraDTO> generarReporteCarreras() {
        try {
            String jpql = "SELECT new dtos.ReporteCarreraDTO(c.nombre, " +
                    "YEAR(i.anioInscripcion), " +
                    "YEAR(i.anioEgreso), " +
                    "(SELECT COUNT(i1) FROM Inscripcion i1 WHERE i1.anioEgreso IS NULL AND i1.carrera = c), " + // Inscripciones sin egreso
                    "(SELECT COUNT(i2) FROM Inscripcion i2 WHERE i2.anioEgreso IS NOT NULL AND i2.carrera = c), " + // Inscripciones con egreso
                    "e.id) " +
                    "FROM Inscripcion i " +
                    "JOIN i.carrera c " +
                    "JOIN i.estudiante e " +
                    "ORDER BY c.nombre ASC, YEAR(i.anioInscripcion) ASC, YEAR(i.anioEgreso) ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al generar reporte de carreras: " + e.getMessage());
            return null;
        }
    }
}
