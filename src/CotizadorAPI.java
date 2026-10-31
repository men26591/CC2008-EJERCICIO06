import java.util.Map;

//simula un servicio que calcula cotizaciones de envío
public class CotizadorAPI extends Integracion implements Cotizable {

    // crea el cotizador con su configuración inicial
    public CotizadorAPI(String id, String proveedor, boolean conectada, double latencia,
                        Map<String, String> parametros) {
        super(id, proveedor, true, conectada, latencia, parametros);
    }

    // devuelve una tarifa de ejemplo para el trayecto solicitado ademas, usa double
    @Override
    public double cotizar(String origen, String destino) {
        return 18.50;
    }
}
