# Prototipo de integraciones de comercio electrónico
proyecto de consola Java que simula integraciones externas en memoria. No utiliza APIs reales.
## Requisitos
- JDK 8 o superior
- Terminal ubicada en la carpeta `integraciones-ecommerce`
## Compilar y ejecutar
```bash
javac -d out src/integraciones/*.java
java -cp out integraciones.Main
```
## Menú
1. Lista las integraciones activas e indica si están conectadas.
2. Busca una integración por identificador y enseña sus parámetros.
3. Ordena por latencia; los empates se ordenan por identificador ascendente.
4. Muestra las operaciones disponibles de una integración.
5. Ejecuta operaciones simuladas según las interfaces que implementa.
6. Consulta el historial de una integración auditable.
7. Prueba un checkout: si una API logística no admite el reintegro, informa que no está disponible y continúa con el cobro en una pasarela de pago.
0. Cierra el programa.
## Organización MVC
- `CatalogoModel` conserva la única lista polimórfica `List<Integracion>`
- `ConsolaView` recibe las entradas y muestra mensajes
- `IntegracionController` coordina las acciones y verifica capacidades con las interfaces
- Las clases `*API` simulan distintos proveedores y sus operaciones
