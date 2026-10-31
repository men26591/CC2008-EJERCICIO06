import java.util.ArrayList;
import java.util.List;

// coordina las opciones del administrador, el modelo y la vista
public class IntegracionController {
    private final CatalogoModel modelo;
    private final ConsolaView vista;

    // guarda las referencias al catálogo y a la consola que usará el controlador
    public IntegracionController(CatalogoModel modelo, ConsolaView vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    // mantiene el menú activo hasta que el usuario elija salir y tiene sus respectivos breaks
    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            vista.mostrarMenu();
            String opcion = vista.leerTexto("Opción: ");
            switch (opcion) {
                case "1":
                    listar(false);
                    break;
                case "2":
                    buscarYMostrar();
                    break;
                case "3":
                    listar(true);
                    break;
                case "4":
                    mostrarOperaciones(vista.leerTexto("Identificador: "));
                    break;
                case "5":
                    ejecutarOperacion();
                    break;
                case "6":
                    consultarAuditoria(vista.leerTexto("Identificador: "));
                    break;
                case "7":
                    procesarCheckout();
                    break;
                case "0":
                    continuar = false;
                    vista.mostrar("Programa finalizado.");
                    break;
                default:
                    vista.mostrar("Opción no válida. Elige un número del menú.");
            }
        }
    }

    //lista las integraciones activas y opcionalmente usa el orden natural
    private void listar(boolean ordenar) {
        List<Integracion> lista = ordenar ? modelo.obtenerOrdenadas() : modelo.obtenerTodas();
        vista.mostrar(ordenar ? "\nCatálogo ordenado por latencia:" : "\nIntegraciones activas:");
        for (Integracion integracion : lista) {
            if (integracion.estaActiva()) {
                vista.mostrar(integracion.toString());
            }
        }
    }

    //busca una integración y enseña su estado y configuración. */
    private void buscarYMostrar() {
        Integracion integracion = modelo.buscar(vista.leerTexto("Identificador: "));
        if (integracion == null) {
            vista.mostrar("No se encontró una integración con ese identificador.");
            return;
        }
        vista.mostrar(integracion.toString());
        vista.mostrar("Parámetros de configuración: " + integracion.getParametros());
    }

    // enumera las capacidades que la integración ofrece mediante sus interfaces. */
    public void mostrarOperaciones(String id) {
        Integracion integracion = modelo.buscar(id);
        if (!validarDisponible(integracion)) {
            return;
        }
        List<String> operaciones = new ArrayList<>();
        if (integracion instanceof EstadoConsultable) operaciones.add("consultarEstado");
        if (integracion instanceof Cotizable) operaciones.add("cotizar");
        if (integracion instanceof Cobrable) operaciones.add("cobrar");
        if (integracion instanceof Reintegrable) operaciones.add("reintegrar");
        if (integracion instanceof Documentable) operaciones.add("generarDocumento");
        if (integracion instanceof Auditable) {
            operaciones.add("registrarEvento");
            operaciones.add("consultarHistorialEventos");
        }
        vista.mostrar("Operaciones disponibles para " + id + ": " + operaciones);
    }

    // pide la integración y la operación, luego invoca solo la capacidad compatible. */
    private void ejecutarOperacion() {
        String id = vista.leerTexto("Identificador: ");
        Integracion integracion = modelo.buscar(id);
        if (!validarDisponible(integracion)) {
            return;
        }
        mostrarOperaciones(id);
        String operacion = vista.leerTexto("Escribe la operación que deseas ejecutar: ");
        ejecutarOperacionElegida(integracion, operacion);
    }

    // ejecuta la acción seleccionada y avisa con claridad si no está disponible. */
    private void ejecutarOperacionElegida(Integracion integracion, String operacion) {
        String resultado;
        if (operacion.equalsIgnoreCase("consultarEstado")
                && integracion instanceof EstadoConsultable) {
            resultado = ((EstadoConsultable) integracion).consultarEstado(
                    vista.leerTexto("Referencia o guía: "));
        } else if (operacion.equalsIgnoreCase("cotizar")
                && integracion instanceof Cotizable) {
            String origen = vista.leerTexto("Origen: ");
            String destino = vista.leerTexto("Destino: ");
            double monto = ((Cotizable) integracion).cotizar(origen, destino);
            resultado = "Cotización simulada: Q" + monto;
        } else if (operacion.equalsIgnoreCase("cobrar")
                && integracion instanceof Cobrable) {
            double monto = vista.leerDouble("Monto a cobrar: Q");
            resultado = ((Cobrable) integracion).cobrar(monto);
        } else if (operacion.equalsIgnoreCase("reintegrar")
                && integracion instanceof Reintegrable) {
            String referencia = vista.leerTexto("Referencia del cobro: ");
            double monto = vista.leerDouble("Monto a reintegrar: Q");
            resultado = ((Reintegrable) integracion).reintegrar(referencia, monto);
        } else if (operacion.equalsIgnoreCase("generarDocumento")
                && integracion instanceof Documentable) {
            resultado = ((Documentable) integracion).generarDocumento(
                    vista.leerTexto("Datos del documento: "));
        } else if (operacion.equalsIgnoreCase("registrarEvento")
                && integracion instanceof Auditable) {
            ((Auditable) integracion).registrarEvento(vista.leerTexto("Descripción del evento: "));
            resultado = "Evento registrado.";
        } else if (operacion.equalsIgnoreCase("consultarHistorialEventos")
                && integracion instanceof Auditable) {
            resultado = "Historial: "
                    + ((Auditable) integracion).consultarHistorialEventos();
        } else {
            vista.mostrar("Esa operación no está disponible para esta integración.");
            return;
        }
        vista.mostrar(resultado);
    }

    // consulta eventos únicamente si la integración tiene la capacidad Auditable. */
    private void consultarAuditoria(String id) {
        Integracion integracion = modelo.buscar(id);
        if (!validarDisponible(integracion)) {
            return;
        }
        if (integracion instanceof Auditable) {
            vista.mostrar("Historial: "
                    + ((Auditable) integracion).consultarHistorialEventos());
        } else {
            vista.mostrar("Esta integración no ofrece auditoría.");
        }
    }

    // demuestra que un reintegro no disponible no impide continuar con el cobro. */
    private void procesarCheckout() {
        String idReintegro = vista.leerTexto(
                "Identificador para solicitar reintegro (prueba LOG-01): ");
        Integracion integracionReintegro = modelo.buscar(idReintegro);
        if (!validarDisponible(integracionReintegro)) {
            return;
        }

        if (integracionReintegro instanceof Reintegrable) {
            String referencia = vista.leerTexto("Referencia del cobro: ");
            double monto = vista.leerDouble("Monto a reintegrar: Q");
            vista.mostrar(((Reintegrable) integracionReintegro)
                    .reintegrar(referencia, monto));
        } else {
            vista.mostrar("El reintegro no está disponible para " + idReintegro
                    + "; el checkout continuará.");
        }

        String idPago = vista.leerTexto("Identificador de la pasarela de pago: ");
        Integracion integracionPago = modelo.buscar(idPago);
        if (!validarDisponible(integracionPago)) {
            return;
        }
        if (integracionPago instanceof Cobrable) {
            double montoCompra = vista.leerDouble("Monto de la compra: Q");
            vista.mostrar(((Cobrable) integracionPago).cobrar(montoCompra));
        } else {
            vista.mostrar("Esta integración no permite cobrar; el checkout no puede completar el pago.");
        }
    }

    // comprueba existencia y conexión antes de intentar una operación. */
    private boolean validarDisponible(Integracion integracion) {
        if (integracion == null) {
            vista.mostrar("No se encontró una integración con ese identificador.");
            return false;
        }
        if (!integracion.estaActiva()) {
            vista.mostrar("La integración está inactiva.");
            return false;
        }
        if (!integracion.estaConectada()) {
            vista.mostrar("La integración existe, pero está desconectada.");
            return false;
        }
        return true;
    }
}
