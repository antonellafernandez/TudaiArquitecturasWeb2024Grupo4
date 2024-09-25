package repositories;

import repositories.interfaces.Repository;
import entities.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public class JpaEstudianteRepository implements Repository<Estudiante> {
    private EntityManager em;
    private static JpaEstudianteRepository instance;

    private JpaEstudianteRepository(EntityManager em) {
        this.em = em;
    }

    public static JpaEstudianteRepository getInstance(EntityManager em) {
        if(instance != null)
            instance = new JpaEstudianteRepository(em);
        return instance;
    }

    // Al tener cascade = CascadeType.ALL, cualquier operación realizada en la entidad Estudiante
    // (insertar, actualizar, eliminar) también afectará automáticamente a las entidades relacionadas
    @Override
    // a) Dar de alta un estudiante
    public void save(Estudiante estudiante) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if(estudiante.getId() == 0){
            try {
                em.persist(estudiante);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar estudiante! " + e.getMessage());
                throw e;
            }
        }
        else{
            try {
                em.merge(estudiante);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al actualizar estudiante! " + e.getMessage());
                throw e;
            }
        }
    }

    @Override
    public Estudiante selectById(int id) {
        try {
            return em.createQuery("SELECT e FROM Estudiante e WHERE e.id = :id", Estudiante.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por id! " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Estudiante> selectAll() {
        try {
            return em.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiantes! " + e.getMessage());
            throw e;
        }
    }

    // Al tener cascade = CascadeType.ALL, cualquier operación realizada en la entidad Estudiante
    // (insertar, actualizar, eliminar) también afectará automáticamente a las entidades relacionadas
    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Estudiante estudiante = em.find(Estudiante.class, id);

            if (estudiante != null) {
                em.remove(estudiante);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                System.out.println("El estudiante con id=" + id + " no existe!");
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al eliminar estudiante! " + e.getMessage());
            return false;
        }
    }

    // b) Matricular un estudiante en una carrera (Consulta implementada en JpaInscripcionDAO)

    // c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple -> Por nombre
    public List<Estudiante> obtenerEstudiantesOrdenadosPorNombre() {
        try {
            return em.createQuery("SELECT e FROM Estudiante e ORDER BY e.nombres", Estudiante.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiantes ordenados por nombre! " + e.getMessage());
            throw e;
        }
    }

    // d) Recuperar un estudiante en base a su número de libreta universitaria
    public Estudiante obtenerEstudiantePorLu(long lu) {
        try {
            return em.createQuery("SELECT e FROM Estudiante e WHERE e.lu = :lu", Estudiante.class)
                    .setParameter("lu", lu)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por lu! " + e.getMessage());
            throw e;
        }
    }

    // e) Recuperar todos los estudiantes en base a su género
    public List<Estudiante> obtenerEstudiantesPorGenero(String genero) {
        try {
            return em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
                    .setParameter("genero", genero)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por lu! " + e.getMessage());
            throw e;
        }
    }

    // f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos (Consulta implementada en JpaInscripcionDAO)

    // g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia (Consulta implementada en JpaInscripcionDAO)
}
