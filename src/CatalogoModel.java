import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CatalogoModel {

    private List<Integracion> integraciones;

    // crea la lista y carga las integraciones iniciales
    public CatalogoModel() {
        integraciones = new ArrayList<>();
        cargarInicial();
    }

    // crea un parámetro de configuración para una integración
    private Map<String, String> crearParametros(String clave, String valor) {
        Map<String, String> parametros = new LinkedHashMap<>();
        parametros.put(clave, valor);
        return parametros;
    }

    // agrega las diez integraciones de ejemplo al catálogo
    private void cargarInicial() {
        integraciones.add(new PagoAPI("PAG-01", "CardPay", true, 180,
                crearParametros("moneda", "GTQ")));
        integraciones.add(new PagoAPI("PAG-02", "TransferGT", true, 210,
                crearParametros("tipo", "transferencia")));
        integraciones.add(new PagoAPI("PAG-03", "WalletGo", true, 180,
                crearParametros("tipo", "billetera digital")));

        integraciones.add(new LogisticaAPI("LOG-01", "RutaExpress", true, 130,
                crearParametros("zona", "urbana")));
        integraciones.add(new LogisticaAPI("LOG-02", "EnvioYa", true, 130,
                crearParametros("zona", "nacional")));
        integraciones.add(new LogisticaAPI("LOG-03", "CargoNorte", false, 240,
                crearParametros("zona", "regional")));

        integraciones.add(new FELAPI("FEL-01", "CertiGT", true, 300,
                crearParametros("ambiente", "pruebas")));
        integraciones.add(new FELAPI("FEL-02", "FacturaCloud", true, 280,
                crearParametros("ambiente", "pruebas")));

        integraciones.add(new RastreoAPI("TRA-01", "TrackX", true, 125,
                crearParametros("búsqueda", "por guía")));
        integraciones.add(new CotizadorAPI("COT-01", "CotizaEnvio", true, 175,
                crearParametros("moneda", "GTQ")));
    }

    // busca una integración por su identificador
    public Integracion buscar(String id) {
        for (Integracion integracion : integraciones) {
            if (integracion.getIdentificador().equalsIgnoreCase(id)) {
                return integracion;
            }
        }

        return null;
    }

    // devuelve la lista que guarda las integraciones
    public List<Integracion> obtenerTodas() {
        return integraciones;
    }

    // ordena la lista por latencia y después por identificador
    public List<Integracion> obtenerOrdenadas() {
        Collections.sort(integraciones);
        return integraciones;
    }
}