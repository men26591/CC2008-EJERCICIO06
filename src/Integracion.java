import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// guarda los datos que comparten todos los proveedores del catálogo
public abstract class Integracion implements Comparable<Integracion> {
    protected final String identificador;
    protected final String proveedor;
    protected final boolean activa;
    protected final boolean conectada;
    protected final double latenciaPromedio;
    protected final Map<String, String> parametros;
    protected final List<String> eventos;

    // inicializa los datos comunes usados para reconocer y ordenar una integración
    public Integracion(String identificador, String proveedor, boolean activa,
                       boolean conectada, double latenciaPromedio,
                       Map<String, String> parametros) {
        this.identificador = identificador;
        this.proveedor = proveedor;
        this.activa = activa;
        this.conectada = conectada;
        this.latenciaPromedio = latenciaPromedio;
        this.parametros = new LinkedHashMap<>(parametros);
        this.eventos = new ArrayList<>();
    }

    // devuelve el identificador usado para buscar esta integración
    public String getIdentificador() {
        return identificador;
    }

    // devuelve la latencia que se usa para ordenar el catálogo
    public double getLatenciaPromedio() {
        return latenciaPromedio;
    }

    // devuelve una copia de los parámetros de configuración
    public Map<String, String> getParametros() {
        return new LinkedHashMap<>(parametros);
    }

    // indica si la integración forma parte del catálogo activo
    public boolean estaActiva() {
        return activa;
    }

    // indica el estado de conexión que se mostrará al administrador
    public boolean estaConectada() {
        return conectada;
    }

    // convierte el estado de conexión en texto para mostrarlo en consola
    public String getEstadoConexion() {
        return conectada ? "Conectada" : "Desconectada";
    }

    // ordena primero por latencia y después por identificador ascendente
    @Override
    public int compareTo(Integracion otra) {
        int comparacionLatencia = Double.compare(latenciaPromedio, otra.latenciaPromedio);
        if (comparacionLatencia != 0) {
            return comparacionLatencia;
        }
        return identificador.compareTo(otra.identificador);
    }

    /** Prepara una línea legible con los datos principales de la integración. */
    @Override
    public String toString() {
        return identificador + " | " + proveedor + " | " + getEstadoConexion()
                + " | " + latenciaPromedio + " ms";
    }
}
