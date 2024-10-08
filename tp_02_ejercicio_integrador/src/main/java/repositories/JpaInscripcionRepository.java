package repositories;

import dtos.CarreraConCantInscriptosDTO;
import dtos.EstudianteDTO;
import dtos.InscripcionDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import repositories.interfaces.RepositoryInscripcion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;

// Singleton
public class JpaInscripcionRepository implements RepositoryInscripcion {
    private EntityManager em;
    private static JpaInscripcionRepository instance;

    private JpaInscripcionRepository(EntityManager em) {
        this.em = em;
    }

    public static JpaInscripcionRepository getInstance(EntityManager em) {
        if(instance == null)
            instance = new JpaInscripcionRepository(em);
        return instance;
    }

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public void save(Inscripcion inscripcion) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if (inscripcion.getId() == 0){
            try {
                em.persist(inscripcion);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar inscripcion! " + e.getMessage());
                throw e;
            }
        } else { // Si la inscripción ya existe, hace update
            try {
                em.merge(inscripcion);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al actualizar inscripcion! " + e.getMessage());
                throw e;
            }
        }
    }

    @Override
    public InscripcionDTO selectById(int id) {
        try {
            return em.createQuery(
                    "SELECT new dtos.InscripcionDTO(i.antiguedad, i.anioInscripcion, i.anioEgreso, i.graduado, c.nombre, e.lu) " +
                            "FROM Inscripcion i " +
                            "JOIN i.carrera c " +
                            "JOIN i.estudiante e " +
                            "WHERE i.id = :id"
                            , InscripcionDTO.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener inscripcion por id! " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<InscripcionDTO> selectAll() {
        try {
            return em.createQuery(
                            "SELECT new dtos.InscripcionDTO(i.antiguedad, i.anioInscripcion, i.anioEgreso, i.graduado, c.nombre, e.lu) " +
                                    "FROM Inscripcion i " +
                                    "JOIN i.carrera c " +
                                    "JOIN i.estudiante e"
                            , InscripcionDTO.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener inscripciones! " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            // Buscar la inscripción por ID
            Inscripcion inscripcion = em.find(Inscripcion.class, id);

            if (inscripcion != null) {
                // Eliminar la inscripción
                em.remove(inscripcion);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                System.out.println("La inscripción con id=" + id + " no existe!");
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al eliminar inscripción! " + e.getMessage());
            return false;
        }
    }

    // 2b) Matricular un estudiante en una carrera
    public void matricularEstudianteEnCarrera(Estudiante estudiante, Carrera carrera, int antiguedad, LocalDate anioInscripcion, LocalDate anioEgreso, boolean graduado) {
        Inscripcion inscripcion = new Inscripcion(antiguedad, anioInscripcion, anioEgreso, graduado, carrera, estudiante);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(inscripcion);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("Error al matricular estudiante! " + e.getMessage());
            throw e;
        }
    }

    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    public List<CarreraConCantInscriptosDTO> recuperarCarrerasOrdenadasPorCantidadInscriptos() {
        try {
            return em.createQuery(
                            "SELECT new dtos.CarreraConCantInscriptosDTO(i.carrera.nombre, COUNT(i)) " +
                                    "FROM Inscripcion i " +
                                    "GROUP BY i.carrera.nombre " +
                                    "HAVING COUNT(i) > 0 " + // Solo incluir carreras con al menos un inscripto
                                    "ORDER BY COUNT(i) DESC", CarreraConCantInscriptosDTO.class)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carreras con inscriptos! " + e.getMessage());
            throw e;
        }
    }

    // 2g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
    public List<EstudianteDTO> recuperarEstudiantesPorCarreraYCiudad(String carrera, String ciudadResidencia) {
        try {
            return em.createQuery(
                            "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                                    "FROM Inscripcion i " +
                                    "JOIN i.estudiante e " +
                                    "WHERE i.carrera.nombre= :carrera " +
                                    "AND e.ciudadResidencia = :ciudad"
                            , EstudianteDTO.class)
                    .setParameter("carrera", carrera)
                    .setParameter("ciudad", ciudadResidencia)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiantes por carrera y residencia! " + e.getMessage());
            throw e;
        }
    }
}
