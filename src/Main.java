public class Main {
    // crea el modelo, la vista y el controlador, y arranca el menú
    public static void main(String[] args) {
        CatalogoModel modelo = new CatalogoModel();
        ConsolaView vista = new ConsolaView();
        IntegracionController controlador = new IntegracionController(modelo, vista);
        controlador.iniciar();
    }
}
