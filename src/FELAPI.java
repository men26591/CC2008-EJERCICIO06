import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// simula un certificador que genera FEL y conserva auditoría
public class FELAPI extends Integracion implements Documentable, Auditable {

    // crea el certificador con sus datos de conexión 
    public FELAPI(String id, String proveedor, boolean conectada, double latencia,
                  Map<String, String> parametros) {
        super(id, proveedor, true, conectada, latencia, parametros);
    }

    // simula la generación del XML de una factura FEL
    @Override
    public String generarDocumento(String datos) {
        registrarEvento("Factura FEL generada");
        return "XML de factura FEL generado (simulado).";
    }

    // registra un evento de facturación electrónica
    @Override
    public void registrarEvento(String evento) {
        eventos.add(evento);
    }

    // devuelve una copia del historial de facturación
    @Override
    public List<String> consultarHistorialEventos() {
        return new ArrayList<>(eventos);
    }
}
