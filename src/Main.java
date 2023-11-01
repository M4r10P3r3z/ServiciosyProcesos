import controlador.Controlador;
import resources.Constantes;

public class Main {
    public static void main(String[] args) {
        Controlador controlador = new Controlador(null, Constantes.NUMERO_HILOS_POR_DEFECTO,
                Constantes.NUMERO_HILOS_POR_DEFECTO);
        controlador.iniciar();
    }
}
