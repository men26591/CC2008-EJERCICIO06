import java.util.List;
import java.util.Map;

// simula una pasarela de pago que puede consultar, cotizar, cobrar y reintegrar
public class PagoAPI extends Integracion implements EstadoConsultable, Cotizable,
        Cobrable, Reintegrable, Auditable {

    // crea una pasarela de pago con los datos iniciales del catálogo
    public PagoAPI(String id, String proveedor, boolean conectada, double latencia,
                   Map<String, String> parametros) {
        super(id, proveedor, true, conectada, latencia, parametros);
    }

    // devuelve un estado ficticio para la referencia de pago recibida
    @Override
    public String consultarEstado(String referencia) {
        return "Pago " + referencia + ": aprobado (simulado).";
    }
    
    //calcula una cotización sencilla para demostrar esta capacidad. */
    @Override
    public double cotizar(String origen, String destino) {
        return 0.03;
    }
    // simula el cobro y deja constancia en la auditoría
    @Override
    public String cobrar(double monto) {
        registrarEvento("Cobro simulado de Q" + monto);
        return "Cobro simulado aprobado por Q" + monto + ".";
    }

    // simula el reintegro y deja constancia en la auditoría
    @Override
    public String reintegrar(String referencia, double monto) {
        registrarEvento("Reintegro simulado de Q" + monto + " para " + referencia);
        return "Reintegro simulado procesado para " + referencia + ".";
    }

    //añade un evento al historial de esta pasarela
    @Override
    public void registrarEvento(String evento) {
        eventos.add(evento);
    }

    // entrega el historial de auditoría sin compartir la lista interna
    @Override
    public List<String> consultarHistorialEventos() {
        return new java.util.ArrayList<>(eventos);
    }
}
