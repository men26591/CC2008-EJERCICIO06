import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// simula una API dedicada a rastrear paquetes y guardar eventos de consulta
public class RastreoAPI extends Integracion implements EstadoConsultable, Auditable {

    // Crea la API de rastreo con sus parámetros iniciales
    public RastreoAPI(String id, String proveedor, boolean conectada, double latencia,
                      Map<String, String> parametros) {
        super(id, proveedor, true, conectada, latencia, parametros);
    }

    // devuelve el estado simulado del paquete identificado por la referencia
    @Override
    public String consultarEstado(String referencia) {
        registrarEvento("Consulta de rastreo para " + referencia);
        return "Paquete " + referencia + ": recibido por el transportista (simulado).";
    }

    // guarda una búsqueda u otro evento de rastreo
    @Override
    public void registrarEvento(String evento) {
        eventos.add(evento);
    }

    // devuelve una copia del historial de rastreo
    @Override
    public List<String> consultarHistorialEventos() {
        return new ArrayList<>(eventos);
    }
}
