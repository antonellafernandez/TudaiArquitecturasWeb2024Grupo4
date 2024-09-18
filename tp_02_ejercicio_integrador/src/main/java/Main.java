import daos.interfaces.DAO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import factories.DAOFactory;
import factories.JpaMySqlDAOFactory;

public class Main {
    public static void main(String[] args) {
        Estudiante e1 = new Estudiante("Nombre1",
                "Apellido1",
                20,
                "X",
                28563588,
                "Ciudad1",
                20);
        Estudiante e2 = new Estudiante("Nombre2",
                "Apellido2",
                21,
                "Y",
                28563444,
                "Ciudad2",
                21);
        Estudiante e3 = new Estudiante("Nombre3",
                "Apellido3",
                23,
                "Z",
                28555588,
                "Ciudad1",
                22);
        Estudiante e4 = new Estudiante("Nombre4",
                "Apellido4",
                24,
                "X",
                28222588,
                "Ciudad4",
                24);
        Estudiante e5 = new Estudiante("Nombre5",
                "Apellido5",
                25,
                "Y",
                28563998,
                "Ciudad5",
                25);
        Estudiante e6 = new Estudiante("Nombre6",
                "Apellido6",
                26,
                "Z",
                29863588,
                "Ciudad6",
                26);
        Estudiante e7 = new Estudiante("Nombre7",
                "Apellido7",
                27,
                "X",
                28563587,
                "Ciudad7",
                27);
        Estudiante e8 = new Estudiante("Nombre8",
                "Apellido8",
                28,
                "X",
                28583588,
                "Ciudad2",
                28);
        Estudiante e9 = new Estudiante("Nombre9",
                "Apellido9",
                29,
                "X",
                28963588,
                "Ciudad2",
                29);
        Estudiante e10 = new Estudiante("Nombre10",
                "Apellido10",
                30,
                "X",
                28563580,
                "Ciudad3",
                30);

        Carrera c1 = new Carrera("Carrera1");
        Carrera c2 = new Carrera("Carrera2");
        Carrera c4 = new Carrera("Carrera4");
        Carrera c5 = new Carrera("Carrera5");

        Inscripcion i1 = new Inscripcion(12, true, c1, e1);
        Inscripcion i2 = new Inscripcion(12, true, c1, e2);
        Inscripcion i3 = new Inscripcion(12, false, c1, e3);
        Inscripcion i4 = new Inscripcion(12, true, c2, e4);
        Inscripcion i5 = new Inscripcion(12, true, c2, e5);
        Inscripcion i6 = new Inscripcion(12, false, c4, e6);
        Inscripcion i7 = new Inscripcion(12, true, c4, e7);
        Inscripcion i8 = new Inscripcion(12, false, c4, e8);
        Inscripcion i9 = new Inscripcion(12, false, c4, e9);
        Inscripcion i10 = new Inscripcion(12, false, c5, e10);

        DAOFactory mySqlDAO1 = JpaMySqlDAOFactory.getDAOFactory(1);
        DAOFactory mySqlDAO2 = JpaMySqlDAOFactory.getDAOFactory(1);
        DAOFactory mySqlDAO3 = JpaMySqlDAOFactory.getDAOFactory(1);
        DAO<Estudiante> jpaEstudianteDAO = mySqlDAO1.getEstudianteDAO();
        DAO<Carrera> jpaCarreraDAO = mySqlDAO2.getCarreraDAO();
        DAO<Inscripcion> jpaInscripcionDAO = mySqlDAO3.getInscripcionDAO();

        jpaEstudianteDAO.insert(e1);
        jpaEstudianteDAO.insert(e2);
        jpaEstudianteDAO.insert(e3);
        jpaEstudianteDAO.insert(e4);
        jpaEstudianteDAO.insert(e5);
        jpaEstudianteDAO.insert(e6);
        jpaEstudianteDAO.insert(e7);
        jpaEstudianteDAO.insert(e8);
        jpaEstudianteDAO.insert(e9);
        jpaEstudianteDAO.insert(e10);

        jpaCarreraDAO.insert(c1);
        jpaCarreraDAO.insert(c2);
        jpaCarreraDAO.insert(c4);
        jpaCarreraDAO.insert(c5);

        jpaInscripcionDAO.insert(i1);
        jpaInscripcionDAO.insert(i2);
        jpaInscripcionDAO.insert(i3);
        jpaInscripcionDAO.insert(i4);
        jpaInscripcionDAO.insert(i5);
        jpaInscripcionDAO.insert(i6);
        jpaInscripcionDAO.insert(i7);
        jpaInscripcionDAO.insert(i8);
        jpaInscripcionDAO.insert(i9);
        jpaInscripcionDAO.insert(i10);

    }
}
