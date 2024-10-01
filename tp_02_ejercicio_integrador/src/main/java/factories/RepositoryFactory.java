package factories;

import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

// Combinación de AbstractFactory y FactoryMethod
// Permite gestionar distintos tipos de persistencia
// Hay que definir la inicialización de cada una de las bases de datos

public abstract class RepositoryFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract RepositoryCarrera getCarreraRepository();
    public abstract RepositoryEstudiante getEstudianteRepository();
    public abstract RepositoryInscripcion getInscripcionRepository();

    public static RepositoryFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC:
                return new JpaMySqlRepositoryFactory();
            case DERBY_JDBC:
                return new JpaDerbyRepositoryFactory();
            default:
                return null;
        }
    }
}
