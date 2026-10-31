// capacidad para calcular el costo estimado de un servicio
public interface Cotizable {
    // calcula un costo estimado entre un origen y un destino
    double cotizar(String origen, String destino);
}
