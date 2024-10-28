@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    public List<Parada> getAllParadas() {
        return paradaRepository.findAll();
    }

    public Optional<Parada> getParadaById(Long id) {
        return paradaRepository.findById(id);
    }

    public Parada createParada(Parada parada) {
        return paradaRepository.save(parada);
    }

    public Parada updateParada(Long id, Parada parada) {
        return paradaRepository.findById(id)
                .map(existingParada -> {
                    parada.setIdParada(existingParada.getIdParada());
                    return paradaRepository.save(parada);
                })
                .orElseThrow(() -> new EntityNotFoundException("Parada no encontrada"));
    }

    public void deleteParada(Long id) {
        paradaRepository.deleteById(id);
    }
}
