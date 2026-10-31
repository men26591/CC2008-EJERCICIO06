import java.util.Scanner;

// maneja la entrada y salida de texto del administrador en consola
public class ConsolaView {
    private final Scanner entrada;

    // prepara la consolaview, que recibe las respuestas escritas por el usuario
    public ConsolaView() {
        entrada = new Scanner(System.in);
    }
    // muestra el mensaje
    public void mostrar(String mensaje) {
        System.out.println(mensaje);
    }
    // pide entrar texto y devuelve la respuesta escrita por el usuario
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    //pide un número y vuelve a preguntar si la entrada no es válida. */
    public double leerDouble(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException error) {
                mostrar("Escribe un número válido, por ejemplo 25.50.");
            }
        }
    }

    // presenta las opciones principales del programa
    public void mostrarMenu() {
        mostrar("\n ---> CATÁLOGO DE INTEGRACIONES <---");
        mostrar("1. Listar integraciones activas");
        mostrar("2. Buscar integración y ver configuración");
        mostrar("3. Ordenar catálogo por latencia");
        mostrar("4. Ver operaciones disponibles");
        mostrar("5. Ejecutar una operación simulada");
        mostrar("6. Consultar historial de auditoría");
        mostrar("7. Probar checkout: reintegro opcional y cobro");
        mostrar("0. Salir");
    }
}
