
package Monopoli;

import java.util.ArrayList;


public class Jugador {
    private String nombre;
    private int posicion;
    private int dinero;
    private ArrayList<Propiedad> propiedades;
    private boolean enCarcel;
    private int turnosEnCarcel;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.posicion = 0;  
        this.dinero = 1500; 
        this.propiedades = new ArrayList<>();
    }

    public void moverFicha(int cantidadCasillas) {
        this.posicion = (this.posicion + cantidadCasillas) % 40; 
        System.out.println(this.nombre + " Se ha movido a " + this.posicion);
    }

    public void pagar(int monto) {
        this.dinero -= monto;
        System.out.println(this.nombre + " ha pagado " + monto + " y ahora tiene " + this.dinero);
        
    }

    public void recibirDinero(int monto) {
        this.dinero += monto;
        System.out.println(this.nombre + " recibe " + monto + " y ahora tiene " + this.dinero);
    }

    public void comprarPropiedad(Propiedad propiedad) {
        if (dinero >= propiedad.getCosto()) {
            this.dinero -= propiedad.getCosto();
            this.propiedades.add(propiedad);
            propiedad.setPropietario(this);
            System.out.println(this.nombre + " ha comprado " + propiedad.getNombre());
        } else {
            System.out.println(this.nombre + " no tiene suficiente dinero para comprar " + propiedad.getNombre());
        }
    }
    
    public void agregarPropiedad(Propiedad propiedad) {
        propiedades.add(propiedad);
    }
    
    public void enviarACarcel() {
        this.enCarcel = true;
        this.turnosEnCarcel = 0;
        this.posicion = 10; 
        System.out.println(nombre + " ha sido enviado a la cárcel.");
    }

    public void intentarSalirDeCarcel() {
        turnosEnCarcel++;
        if (turnosEnCarcel >= 3) {
            pagar(50); 
            enCarcel = false;
            System.out.println(nombre + " ha pagado para salir de la cárcel.");
        }
    }

    public boolean estaEnCarcel() {
        return enCarcel;
    }

   
    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getDinero() {
        return dinero;
    }

    public ArrayList<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
}
