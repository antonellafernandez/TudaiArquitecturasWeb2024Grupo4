package dtos;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReporteCarreraDTO implements Serializable {

    private EntityManager em;

    private Long idCarrera;
    private String nombreCarrera;
    private String descripcionCarrera;
    private Long idEstudiante;
    private String nombreEstudiante;
    private String generoEstudiante;
    private boolean isGraduado;
    private Date fecha;

    public ReporteCarreraDTO() {}
    public ReporteCarreraDTO(EntityManager em) {this.em = em;}

    public List<ReporteCarreraDTO> getReporte(){
        try{
            String jpql = "SELECT e " +
                            "FROM Estudiante e JOIN e.inscripciones i JOIN i.carrera c " +
                            "ORDER BY c.nombre";
            TypedQuery<ReporteCarreraDTO> query = em.createQuery(jpql, ReporteCarreraDTO.class);
            //query.setParameter();
            return query.getResultList();
        }catch (Exception e){

        }
        return null;
    }
}
