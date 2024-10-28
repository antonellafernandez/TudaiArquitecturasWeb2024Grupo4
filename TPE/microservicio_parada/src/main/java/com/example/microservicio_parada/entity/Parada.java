@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParada;

    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "parada")
    private List<Monopatin> monopatines = new ArrayList<>();



    // Método para agregar un monopatín a esta parada
    public void agregarMonopatin(Monopatin monopatin) {
        if (!monopatines.contains(monopatin)) {
            monopatines.add(monopatin);
            monopatin.setParada(this);  // Establece la relación bidireccional
        }
    }

    // Método para quitar un monopatín de esta parada
    public void quitarMonopatin(Monopatin monopatin) {
        if (monopatines.contains(monopatin)) {
            monopatines.remove(monopatin);
            monopatin.setParada(null);  // Elimina la relación bidireccional
        }
    }

}
