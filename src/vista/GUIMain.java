package vista;

import controlador.Controlador;
import resources.Constantes;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class GUIMain extends JFrame {
    private final Controlador controlador = new Controlador(this,
            Constantes.NUMERO_HILOS_POR_DEFECTO, Constantes.NUMERO_HILOS_POR_DEFECTO);
    private JProgressBar pbLlenado;
    private JTextArea taLog;
    private JButton bInicio;
    private JButton bFin;
    private JPanel panelMainPanel;
    private JButton bReset;
    private JButton bAnadirEntrada;
    private JButton bQuitarEntrada;
    private JButton bAnadirSalida;
    private JButton bQuitarSalida;
    private JLabel lEntradasActuales;
    private JLabel lSalidasActuales;
    private JLabel lbEstado;


    public GUIMain() throws HeadlessException {
        setContentPane(panelMainPanel);
        setTitle("Proyecto de Servicios y procesos");

        bFin.addActionListener(e -> terminarControlador());
        bInicio.addActionListener(e -> iniciarControlador());
        bReset.addActionListener(e -> resetear());
        bAnadirEntrada.addActionListener(e -> aniadirEntrada());
        bQuitarEntrada.addActionListener(e -> quitarEntrada());
        bAnadirSalida.addActionListener(e -> aniadirSalida());
        bQuitarSalida.addActionListener(e -> quitarSalida());


        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        escribirEntradasActuales();
        escribirSalidasActuales();
        escribirEstado();
    }

    private void escribirEstado() {
        this.lbEstado.setText((this.controlador.isFuncionando())?"El programa está activo":"El programa está parado");
    }

    private void iniciarControlador() {
        if (!this.controlador.isFuncionando()) {
            this.controlador.iniciar();
            escribirEstado();
        }
    }

    private void terminarControlador() {
        this.controlador.terminar();
        escribirEstado();
    }

    private void escribirSalidasActuales() {
        this.lSalidasActuales.setText(String.valueOf(this.controlador.getNumSalidas()));
    }

    private void escribirEntradasActuales() {
        this.lEntradasActuales.setText(String.valueOf(this.controlador.getNumEntradas()));
    }

    private void quitarSalida() {
        if (this.controlador.getNumSalidas()>Constantes.NUMERO_MIN_HILOS) {
            this.controlador.quitarSalida();
            escribirSalidasActuales();
        }
    }

    private void aniadirSalida() {
        if (this.controlador.getNumSalidas()<Constantes.NUMERO_MAX_HILOS) {
            this.controlador.aniadirSalida();
            escribirSalidasActuales();
        }
    }

    private void quitarEntrada() {
        if (this.controlador.getNumEntradas()>Constantes.NUMERO_MIN_HILOS) {
            this.controlador.quitarEntrada();
            escribirEntradasActuales();
        }
    }

    private void aniadirEntrada() {
        if (this.controlador.getNumEntradas()<Constantes.NUMERO_MAX_HILOS) {
            this.controlador.aniadirEntrada();
            escribirEntradasActuales();
        }
    }

    private void resetear() {
        terminarControlador();
        this.controlador.vaciarLocal();
        this.escribir(null, 0);
    }

    public void escribir(String s, int n) {
        if (s==null) this.taLog.setText(null);
        else this.taLog.append(s);
        this.pbLlenado.setValue(n);
        double aux = (double) n / (double) Constantes.CAPACIDAD_MAX_LOCAL;
        if (aux <= Constantes.CASI_VACIO) this.pbLlenado.setForeground(Color.BLUE);
        else if  (aux <= Constantes.MEDIO) this.pbLlenado.setForeground(Color.GREEN);
        else if (aux <= Constantes.CASI_LLENO) this.pbLlenado.setForeground(Color.ORANGE);
        else this.pbLlenado.setForeground(Color.RED);
    }

    private void createUIComponents() {
        this.pbLlenado = new JProgressBar(Constantes.CAPACIDAD_MIN_LOCAL, Constantes.CAPACIDAD_MAX_LOCAL);
        this.pbLlenado.setStringPainted(true);
        this.taLog = new JTextArea();
        DefaultCaret caret = (DefaultCaret)taLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public static void main(String[] args) {
        new GUIMain();
    }
}
