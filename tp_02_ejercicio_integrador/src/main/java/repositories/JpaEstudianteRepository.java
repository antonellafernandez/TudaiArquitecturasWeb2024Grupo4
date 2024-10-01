package repositories;

import dtos.EstudianteDTO;
import entities.Estudiante;
import repositories.interfaces.RepositoryEstudiante;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

// Singleton
public class JpaEstudianteRepository implements RepositoryEstudiante {
    private EntityManager em;
    private static JpaEstudianteRepository instance;

    private JpaEstudianteRepository(EntityManager em) {
        this.em = em;
    }

    public static JpaEstudianteRepository getInstance(EntityManager em) {
        if(instance == null)
            instance = new JpaEstudianteRepository(em);
        return instance;
    }

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    // Al tener cascade = CascadeType.ALL, cualquier operación realizada en la entidad Estudiante
    // (insertar, actualizar, eliminar) también afectará automáticamente a las entidades relacionadas
    @Override
    // 2a) Dar de alta un estudiante
    public void save(Estudiante estudiante) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if (estudiante.getId() == 0){
            try {
                em.persist(estudiante);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar estudiante! " + e.getMessage());
                throw e;
            }
        } else { // Si el estudiante ya existe, hace update
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
    public EstudianteDTO selectById(int id) {
        try {
            return em.createQuery(
                            "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                                    "FROM Estudiante e " +
                                    "WHERE e.id = :id"
                            , EstudianteDTO.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por id! " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<EstudianteDTO> selectAll() {
        try {
            return em.createQuery(
                            "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +

                                    "FROM Estudiante e"
                        , EstudianteDTO.class)
                    .getResultList();
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

    // 2b) Matricular un estudiante en una carrera (Consulta implementada en JpaInscripcionDAO)

    // 2c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple -> Por nombre
    public List<EstudianteDTO> obtenerEstudiantesOrdenadosPorNombre() {
        try {
            return em.createQuery(
                    "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                            "FROM Estudiante e " +
                            "ORDER BY e.nombres"
                    , EstudianteDTO.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiantes ordenados por nombre! " + e.getMessage());
            throw e;
        }
    }

    // 2d) Recuperar un estudiante en base a su número de libreta universitaria
    public EstudianteDTO obtenerEstudiantePorLu(long lu) {
        try {
            return em.createQuery(
                        "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                                "FROM Estudiante e " +
                                "WHERE e.lu = :lu"
                            , EstudianteDTO.class)
                    .setParameter("lu", lu)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por lu! " + e.getMessage());
            throw e;
        }
    }

    // 2e) Recuperar todos los estudiantes en base a su género
    public List<EstudianteDTO> obtenerEstudiantesPorGenero(String genero) {
        try {
            return em.createQuery(
                            "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                                    "FROM Estudiante e " +
                                    "WHERE e.genero = :genero"
                            , EstudianteDTO.class)
                    .setParameter("genero", genero)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiante por lu! " + e.getMessage());
            throw e;
        }
    }

    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos (Consulta implementada en JpaInscripcionDAO)

    // 2g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia (Consulta implementada en JpaInscripcionDAO)
}
