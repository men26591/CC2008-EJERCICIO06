// capacidad para simular la devolución de un cobro
public interface Reintegrable {
    // devuelve en memoria un monto relacionado con una referencia
    String reintegrar(String referencia, double monto);
}
