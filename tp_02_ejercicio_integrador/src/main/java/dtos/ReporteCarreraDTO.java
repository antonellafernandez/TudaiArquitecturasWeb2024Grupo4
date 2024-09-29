package dtos;

import entities.Estudiante;
import factories.JpaMySqlRepositoryFactory;
import factories.RepositoryFactory;
import repositories.interfaces.Repository;
import java.io.Serializable;

public class ReporteCarreraDTO implements Serializable {
    private String nombreCarrera;
    private Integer anioInscripcion;
    private Integer anioEgreso;
    private Long cantidadInscriptos;
    private Long cantidadEgresados;
    private EstudianteDTO estudiante;

    // Constructores
    public ReporteCarreraDTO() {}

    public ReporteCarreraDTO(String nombreCarrera, Integer anioInscripcion, Integer anioEgreso, Long cantidadInscriptos, Long cantidadEgresados, int idEstudiante) {
        this.nombreCarrera = nombreCarrera;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.cantidadInscriptos = cantidadInscriptos;
        this.cantidadEgresados = cantidadEgresados;
        this.setEstudiante(idEstudiante);
    }

    private void setEstudiante(int idEstudiante) {
        RepositoryFactory mySqlFactory = JpaMySqlRepositoryFactory.getDAOFactory(1);
        Repository<Estudiante> jpaEstudianteRepository = mySqlFactory.getEstudianteRepository();
        Estudiante estudiante = jpaEstudianteRepository.selectById(idEstudiante);

        if (estudiante != null) {
            EstudianteDTO eDTO = new EstudianteDTO(estudiante.getNombres(), estudiante.getApellido(), estudiante.getEdad(), estudiante.getGenero(), estudiante.getDni(), estudiante.getCiudadResidencia(), estudiante.getLu());
            this.estudiante = eDTO;
        } else {
            System.out.println("Estudiante no encontrado");
        }
    }

    // Getters y Setters
    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Integer getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(Integer anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public Integer getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(Integer anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public Long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(Long cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public Long getCantidadEgresados() {
        return cantidadEgresados;
    }

    public void setCantidadEgresados(Long cantidadEgresados) {
        this.cantidadEgresados = cantidadEgresados;
    }

    @Override
    public String toString() {
        return "ReporteCarreraDTO{" +
                "nombreCarrera='" + nombreCarrera + '\'' +
                ", anioInscripcion=" + anioInscripcion +
                ", anioEgreso=" + anioEgreso +
                ", cantidadInscriptos=" + cantidadInscriptos +
                ", cantidadEgresados=" + cantidadEgresados +
                ", estudiante=" + estudiante +
                '}';
    }
}
