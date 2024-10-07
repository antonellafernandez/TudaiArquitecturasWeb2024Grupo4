package springboot.app.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.app.modelos.Carrera;
import springboot.app.repositorios.RepoCarrera;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraServicio {

    @Autowired
    private RepoCarrera repoCarrera;

    // Guardar una nueva carrera
    @Transactional
    public Carrera guardarCarrera(Carrera carrera) throws Exception {
        try {
            return repoCarrera.save(carrera);
        } catch (Exception e) {
            throw new Exception("Error al guardar carrera: " + e.getMessage());
        }
    }

    // Obtener todas las carreras
    @Transactional
    public List<Carrera> findAll() throws Exception {
        try {
            return repoCarrera.findAll();
        } catch (Exception e) {
            throw new Exception("Error al obtener carreras: " + e.getMessage());
        }
    }

    // Obtener una carrera por ID
    @Transactional
    public Carrera findById(Long id) throws Exception {
        try {
            return repoCarrera.findById(id)
                    .orElseThrow(() -> new Exception("Carrera no encontrada con ID: " + id));
        } catch (Exception e) {
            throw new Exception("Error al buscar carrera: " + e.getMessage());
        }
    }

    // Actualizar una carrera
    @Transactional
    public Carrera update(Long id, Carrera carrera) throws Exception {
        try {
            Carrera carreraExistente = findById(id);
            carreraExistente.setNombre(carrera.getNombre());

            return repoCarrera.save(carreraExistente);
        } catch (Exception e) {
            throw new Exception("Error al actualizar carrera: " + e.getMessage());
        }
    }

    // Eliminar una carrera
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            if (repoCarrera.existsById(id)) {
                repoCarrera.deleteById(id);
                return true;
            } else {
                throw new Exception("Carrera no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("Error al eliminar carrera: " + e.getMessage());
        }
    }
}
