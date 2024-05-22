
package Monopoli;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


class Monopoly extends JFrame {
    
    private Juego juego;
    private JPanel panelTablero;
    private JPanel panelInfo;
    private JLabel labelInfo;
    private ArrayList<JLabel> labelsJugadores;
    public JLabel[][] casillasLabels = new JLabel[10][4];

    public Monopoly(List<String> nombresJugadores) {
        setTitle("Monopoly en Java");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        juego = new Juego(nombresJugadores);
        labelsJugadores = new ArrayList<>();

        setupTablero();
        setupPanelInfo();

        setVisible(true);
    }

    private void setupTablero() {
        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(10, 4));

        casillasLabels = new JLabel[10][4];

        List<Casilla> casillasDelJuego = juego.getTablero().getCasillas();
        if (casillasDelJuego.size() != 40) {
            throw new IllegalStateException("Debe haber exactamente 40 casillas definidas.");
        }

        int index = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel labelCasilla = new JLabel();
                labelCasilla.setBorder(BorderFactory.createLineBorder(Color.black));
                labelCasilla.setHorizontalAlignment(JLabel.CENTER);
                labelCasilla.setVerticalAlignment(JLabel.CENTER);
                labelCasilla.setOpaque(true);
  
                    casillasLabels[i][j] = null;
                    panelTablero.add(new JLabel());
                
                    Casilla casilla = casillasDelJuego.get(index);
                    labelCasilla.setText(casilla.getNombre());
                    labelCasilla.setBackground(determinarColorCasilla(casilla));

                    casillasLabels[i][j] = labelCasilla;
                    panelTablero.add(labelCasilla);

                    index++;
                
            }
        }

        add(panelTablero, BorderLayout.CENTER);
    }

    private void setupPanelInfo() {
        JPanel panelInfo = new JPanel(new BorderLayout());
        JPanel panelJugadores = new JPanel(new GridLayout(juego.getJugadores().size(), 1));

        for (Jugador jugador : juego.getJugadores()) {
            JLabel labelJugador = new JLabel(formatoInfoJugador(jugador));
            labelJugador.setBorder(BorderFactory.createLineBorder(Color.black));
            panelJugadores.add(labelJugador);
            labelsJugadores.add(labelJugador);
        }

        panelInfo.add(new JScrollPane(panelJugadores), BorderLayout.CENTER);

        JButton botonLanzarDados = new JButton("Lanzar Dados");
        botonLanzarDados.addActionListener(e -> {
            juego.jugarTurno();
            actualizarUI();
        });
        panelInfo.add(botonLanzarDados, BorderLayout.SOUTH);

        add(panelInfo, BorderLayout.EAST);
    }

    private String formatoInfoJugador(Jugador jugador) {
        return "<html>Jugador: " + jugador.getNombre() +
               "<br/>Dinero: $" + jugador.getDinero() +
               "<br/>Propiedades: " + jugador.getPropiedades().size() + "</html>";
    }

    private void actualizarUI() {
        SwingUtilities.invokeLater(() -> {
            int index = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 4; j++) {
                    if (casillasLabels[i][j] != null) {
                        JLabel labelCasilla = casillasLabels[i][j];
                        Casilla casilla = juego.getTablero().getCasillas().get(index);
                        labelCasilla.setText(casilla.getNombre());
                        labelCasilla.setBackground(determinarColorCasilla(casilla));

                        labelCasilla.setText("<html>" + casilla.getNombre() + "<br/></html>");
                        index++;
                    }
                }
            }

            for (Jugador jugador : juego.getJugadores()) {
            int posicion = jugador.getPosicion();
            int fila = posicion / 4;
            int columna = posicion % 4;
            JLabel casillaLabel = casillasLabels[fila][columna];
            String textoCasilla = "<html>" + juego.getTablero().getCasillas().get(posicion).getNombre() +
                                  "<br/>" + jugador.getNombre() + " aquí</html>";
            casillaLabel.setText(textoCasilla);
            casillaLabel.setBackground(Color.CYAN);  
        }

            for (int i = 0; i < juego.getJugadores().size(); i++) {
                Jugador jugador = juego.getJugadores().get(i);
                JLabel labelJugador = labelsJugadores.get(i);
                labelJugador.setText(formatoInfoJugador(jugador));
            }
        });
    }
    
    private int[] convertirPosicionACoordenadas(int posicion) {
    if (posicion < 10) {
        
        return new int[] {10, posicion}; 
    } else if (posicion < 20) {
       
        return new int[] {20 - posicion, 0};  
    } else if (posicion < 30) {
       
        return new int[] {0, posicion - 20};  
    } else {
        
        return new int[] {posicion - 30, 10};  
    }
}
    
private int convertirCoordenadasAIndex(int fila, int columna) {
    
    if (fila == 10) {
        
        return columna; 
    } else if (columna == 0) {
        
        return 10 + (10 - fila); 
    } else if (fila == 0) {
        
        return 20 + columna; 
    } else if (columna == 10) {
        
        return 30 + (10 - fila); 
    }
    return -1;  
}
    private int[] posicionACoordenadas(int posicion) {
        if (posicion < 10) {
       
        return new int[] {10, posicion};  
    } else if (posicion < 20) {
       
        return new int[] {20 - posicion, 0};  
    } else if (posicion < 30) {
        
        return new int[] {0, posicion - 20};  
    } else {
       
        return new int[] {posicion - 30, 10};  
    }
    }
    
    

    private Color determinarColorCasilla(Casilla casilla) {
        if (casilla instanceof Propiedad) {
            return Color.lightGray;
        } else if (casilla instanceof Carcel) {
            return Color.red;
        } else if (casilla instanceof Impuestos) {
            return Color.orange;
        } else if (casilla instanceof Estacion) {
            return Color.blue;
        } else if (casilla instanceof ServicioPublico) {
            return Color.green;
        } else if (casilla instanceof CajaComunidad || casilla instanceof Suerte) {
            return Color.yellow;
        } else if (casilla instanceof Salida) {
            return Color.pink;
        } else if (casilla instanceof Libre) {
            return Color.white;
        } else {
            return Color.gray;
        }
    }

    private void actualizarLabelJugador(Jugador jugador, JLabel labelJugador) {
        String texto = "<html>Jugador: " + jugador.getNombre() +
                       "<br/>Dinero: $" + jugador.getDinero() +
                       "<br/>Propiedades: " + jugador.getPropiedades().size() + "</html>";
        labelJugador.setText(texto);
    }

    private boolean jugadorEnCasilla(int index) {
        for (Jugador jugador : juego.getJugadores()) {
            if (jugador.getPosicion() == index) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
       
        List<String> nombresJugadores = Arrays.asList();
        new Monopoly(nombresJugadores);
    }
}
