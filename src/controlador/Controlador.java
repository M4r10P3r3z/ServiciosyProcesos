package controlador;

import modelo.Local;
import modelo.PorteroEntrada;
import modelo.PorteroSalida;
import vista.GUIMain;

import java.util.ArrayList;
import java.util.List;

public class Controlador {
    GUIMain guiMain;
    Local local = new Local(this);
    List<Thread> entradas = new ArrayList<>();
    List<Thread> salidas = new ArrayList<>();
    int numEntradas, numSalidas;
    boolean isFuncionando;

    public Controlador(GUIMain guiMain, int numEntradas, int numSalidas) {
        this.guiMain = guiMain;
        this.numEntradas=numEntradas;
        this.numSalidas=numSalidas;
        this.isFuncionando =false;
    }

    public int getNumEntradas() {return numEntradas;}

    public int getNumSalidas() {return numSalidas;}

    public boolean isFuncionando() {return isFuncionando;}

    public void iniciar() {
        for (int i=0;i<numEntradas;i++) {
            entradas.add(new Thread(new PorteroEntrada(local), "Entrada " + (i + 1)));
        }
        for (int i=0;i<numSalidas;i++) {
            salidas.add(new Thread(new PorteroSalida(local), "Salida " + (i + 1)));
        }
        for (Thread thread: entradas) {
            thread.start();
        }
        for (Thread thread: salidas) {
            thread.start();
        }
        this.isFuncionando =true;
    }

    public void terminar() {
        for (Thread thread: entradas) {
            thread.interrupt();
        }
        entradas.clear();
        for (Thread thread: salidas) {
            thread.interrupt();
        }
        salidas.clear();
        this.isFuncionando = false;
    }

    public void notificar(String s, int n) {
        System.out.println(s);
        if (this.guiMain!=null) guiMain.escribir(s + '\n', n);
    }
    public void vaciarLocal() {
        this.local.vaciarLocal();
    }

    public void aniadirEntrada() {
        this.numEntradas++;
        if (isFuncionando()) {
            entradas.add(new Thread(new PorteroEntrada(local), "Entrada " + numEntradas));
            entradas.get(numEntradas-1).start();
        }
    }

    public void quitarEntrada() {
        this.numEntradas--;
        if (isFuncionando()) {
            entradas.get(numEntradas).interrupt();
            entradas.remove(numEntradas);
        }
    }

    public void aniadirSalida() {
        this.numSalidas++;
        if (isFuncionando()) {
            salidas.add(new Thread(new PorteroSalida(local), "Salida " + numSalidas));
            salidas.get(numSalidas-1).start();
        }
    }

    public void quitarSalida() {
        this.numSalidas--;
        if (isFuncionando()) {
            salidas.get(numSalidas).interrupt();
            salidas.remove(numSalidas);
        }
    }
}
