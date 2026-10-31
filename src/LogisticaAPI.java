import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// simula un proveedor logístico
public class LogisticaAPI extends Integracion implements EstadoConsultable,
        Cotizable, Documentable, Auditable {

    // crea el proveedor logístico con su configuración y latencia
    public LogisticaAPI(String id, String proveedor, boolean conectada, double latencia,
                        Map<String, String> parametros) {
        super(id, proveedor, true, conectada, latencia, parametros);
    }

    // devuelve un estado simulado para una guía de envío
    @Override
    public String consultarEstado(String guia) {
        return "Paquete " + guia + ": en tránsito (simulado).";
    }

    // calcula el costo fijo de ejemplo para una ruta
    @Override
    public double cotizar(String origen, String destino) {
        return 25.00;
    }

    //simula la creación de una guía y registra el evento
    @Override
    public String generarDocumento(String datos) {
        registrarEvento("Guía de envío generada");
        return "Guía de envío generada (simulada).";
    }

    // guarda un evento de logística en el historial
    @Override
    public void registrarEvento(String evento) {
        eventos.add(evento);
    }

    // devuelve los eventos logísticos registrados
    @Override
    public List<String> consultarHistorialEventos() {
        return new ArrayList<>(eventos);
    }
}
