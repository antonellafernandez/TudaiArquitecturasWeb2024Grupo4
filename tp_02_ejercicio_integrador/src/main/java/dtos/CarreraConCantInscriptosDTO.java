package dtos;

public class CarreraConCantInscriptosDTO {
    private String nombre;
    private int cantInscriptos;
    public CarreraConCantInscriptosDTO() {}
    public CarreraConCantInscriptosDTO(String nombre, int cantInscriptos) {}

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantInscriptos() {
        return cantInscriptos;
    }

    public void setCantInscriptos(int cantInscriptos) {
        this.cantInscriptos = cantInscriptos;
    }

    @Override
    public String toString() {
        return "CarrerasOrdenadasPorCantInscriptosDTO{" +
                "nombre='" + nombre + '\'' +
                ", cantInscriptos=" + cantInscriptos +
                '}';
    }
}
