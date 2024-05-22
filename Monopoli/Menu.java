
package Monopoli;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class SeleccionJugadoresForm extends JFrame {
    private JComboBox<Integer> comboNumJugadores;
    private JButton botonContinuar;

    public SeleccionJugadoresForm() {
        super("Seleccionar número de jugadores");
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new GridLayout(2, 1));
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Número de Jugadores:"));
        comboNumJugadores = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6});
        add(comboNumJugadores);

        botonContinuar = new JButton("Continuar");
        botonContinuar.addActionListener(e -> {
            int numJugadores = (int) comboNumJugadores.getSelectedItem();
            new Menu(numJugadores);
            dispose();
        });
        add(botonContinuar);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SeleccionJugadoresForm();
    }
}

public class Menu extends JFrame {
    private List<JTextField> camposNombres;
    private JButton botonIniciar;
    private int numJugadores;

    public Menu(int numJugadores) {
        super("Ingresar nombres de los jugadores");
        this.numJugadores = numJugadores;
        camposNombres = new ArrayList<>();
        inicializarUI();
    }


    private void inicializarUI() {
        setLayout(new GridLayout(numJugadores + 1, 2));
        setSize(400, 30 * numJugadores + 70);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < numJugadores; i++) {
            add(new JLabel("Nombre del Jugador " + (i + 1) + ":"));
            JTextField campoTexto = new JTextField();
            camposNombres.add(campoTexto);
            add(campoTexto);
        }

        botonIniciar = new JButton("Iniciar Juego");
        botonIniciar.addActionListener(e -> iniciarJuego());
        add(botonIniciar);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarJuego() {
        List<String> nombresJugadores = new ArrayList<>();
        for (JTextField campo : camposNombres) {
            nombresJugadores.add(campo.getText().trim());
        }

        dispose();

        Monopoly juegoGUI = new Monopoly(nombresJugadores);
        juegoGUI.setVisible(true);
    }
}
