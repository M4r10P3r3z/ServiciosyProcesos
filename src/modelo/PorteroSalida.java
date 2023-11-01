package modelo;

import resources.Constantes;

import java.util.concurrent.ThreadLocalRandom;
public class PorteroSalida implements Runnable{
    private final Local local;
    public PorteroSalida(Local local) {this.local = local;}

    @Override
    public void run() {
        boolean ejecutando = true;
        while (ejecutando) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(Constantes.MINIMO_TIEMPO_ESPERA,
                        Constantes.MAXIMO_TIEMPO_ESPERA));
                local.salirPersona();
            } catch (InterruptedException e) {
                System.out.println("Cerrando hilo: " + Thread.currentThread().getName());
                ejecutando = false;
            }
        }
    }
}
