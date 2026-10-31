import java.util.List;
//registra eventos y consultar su historial
public interface Auditable {
    // añade un evento al historial de auditoría de la integración
    void registrarEvento(String evento);

    // devuelve una copia del historial para evitar modificar la lista interna
    List<String> consultarHistorialEventos();
}
