package modelo;

import controlador.Controlador;
import resources.Constantes;

public class Local {

    private int aforo = Constantes.CAPACIDAD_MIN_LOCAL;
    private final Controlador controlador;

    public Local(Controlador controlador) {this.controlador = controlador;}

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    @Override
    public String toString() {
        switch (this.getAforo()) {
            case Constantes.CAPACIDAD_MIN_LOCAL -> {return "El local está vacío.";}
            case Constantes.CAPACIDAD_MAX_LOCAL -> {return "Hay " + Constantes.CAPACIDAD_MAX_LOCAL
                    + " personas en el local, está lleno";}
            default -> {return "Dentro del local hay " + this.getAforo() +
                    " persona" + ((this.getAforo()>1)?"s":"") + ", todavía hay sitio.";}
        }
    }
    public synchronized void entrarPersona() throws InterruptedException {
        while (this.getAforo()>=Constantes.CAPACIDAD_MAX_LOCAL) {
            wait();
        }
            this.aforo++;
            this.controlador.notificar("Ha entrado una persona en el local por la puerta " +
                    Thread.currentThread().getName() + ". " + this, this.getAforo());
            notify();
    }

    public synchronized void salirPersona() throws InterruptedException {
        while (this.getAforo()==Constantes.CAPACIDAD_MIN_LOCAL) {
            wait();
        }
        this.aforo--;
        this.controlador.notificar("Ha salido una persona del local por la puerta " +
                Thread.currentThread().getName() + ". " + this, this.getAforo());
        notify();
    }

    public void vaciarLocal() {
        setAforo(0);
    }
}
