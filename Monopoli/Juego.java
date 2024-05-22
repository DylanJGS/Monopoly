
package Monopoli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;

public class Juego {
    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Tarjeta> tarjetasCajaComunidad;
    private ArrayList<Tarjeta> tarjetasSuerte;
    private int jugadorActual;
    private boolean juegoTerminado;
    private Scanner scanner;

    public Juego(List<String> nombresJugadores) {
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        inicializarJugadores(nombresJugadores);
        this.jugadorActual = 0;
        this.juegoTerminado = false;
        this.tarjetasCajaComunidad = new ArrayList<>();
        this.tarjetasSuerte = new ArrayList<>();
        inicializarTarjetas();
        
        this.scanner = new Scanner(System.in);
    }
    
    private void inicializarTarjetas() {
        
    tarjetasCajaComunidad.add(new DineroTarjeta("El banco te paga un dividendo de $50.", 50));
    tarjetasCajaComunidad.add(new DineroTarjeta("Devolución de impuestos. Recibe $20.", 20));
    tarjetasCajaComunidad.add(new MoverTarjeta("Ve directo a la cárcel. No pases por la salida.", 10)); 

    
    tarjetasSuerte.add(new DineroTarjeta("Has sido multado por exceso de velocidad. Paga $15.", -15));
    tarjetasSuerte.add(new MoverTarjeta("Avanza hasta la 'Salida'.", 0)); 
    tarjetasSuerte.add(new DineroTarjeta("Tu préstamo de construcción madura. Recibe $150.", 150));

        
        Collections.shuffle(tarjetasCajaComunidad);
        Collections.shuffle(tarjetasSuerte);
    }

    public Tarjeta sacarTarjetaCajaComunidad() {
        Tarjeta tarjeta = tarjetasCajaComunidad.remove(0);
        tarjetasCajaComunidad.add(tarjeta);  
        return tarjeta;
    }

    public Tarjeta sacarTarjetaSuerte() {
        Tarjeta tarjeta = tarjetasSuerte.remove(0);
        tarjetasSuerte.add(tarjeta);  
        return tarjeta;
    }

    private void inicializarJugadores(List<String> nombresJugadores) {
        for (String nombre : nombresJugadores) {
            this.jugadores.add(new Jugador(nombre));
        }
    }
    
    

    public void iniciarJuego() {
        System.out.println("Monopoly");
        while (!juegoTerminado) {
            jugarTurno();
        }
    }

    public void jugarTurno() {
        Jugador jugador = jugadores.get(jugadorActual);
        System.out.println(jugador.getNombre() + ", es tu turno.");

        
        int dado = lanzarDados();
        System.out.println("Has lanzado un " + dado + ".");

        
        jugador.moverFicha(dado);
        int posicion = jugador.getPosicion();
        Casilla casillaActual = tablero.getCasilla(posicion);
        
        System.out.println("Has aterrizado en " + casillaActual.getNombre() + ".");
        
        
        casillaActual.ejecutarAccion(jugador, this);

       
        if (jugador.getDinero() <= 0) {
            System.out.println(jugador.getNombre() + " se ha quedado sin dinero y ha perdido.");
            finalizarJuego(jugador);
        }

        
        jugadorActual = (jugadorActual + 1) % jugadores.size();
    }

    private int lanzarDados() {
        return (int) (Math.random() * 6) + 1; 
        
   }

    public void finalizarJuego(Jugador jugadorPerdedor) {
        System.out.println("El juego ha terminado. " + jugadorPerdedor.getNombre() + " ha perdido.");
        jugadores.remove(jugadorPerdedor);
        if (jugadores.size() == 1) {
            System.out.println("El ganador es " + jugadores.get(0).getNombre() + ".");
        }
        juegoTerminado = true;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }
    
    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public int getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(int jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    
}

