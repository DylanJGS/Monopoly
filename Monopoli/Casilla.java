
package Monopoli;

import javax.swing.JOptionPane;


public abstract class Casilla {
    protected String nombre;

    public Casilla(String nombre) {
        this.nombre = nombre;
    }

    public abstract void ejecutarAccion(Jugador jugador, Juego juego);

    public String getNombre() {
        return nombre;
    }
}

class Propiedad extends Casilla {
    private int costo;
    private Jugador propietario;

    public Propiedad(String nombre, int costo) {
        super(nombre);
        this.costo = costo;
        this.propietario = null;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        if (propietario == null) {
            int respuesta = JOptionPane.showConfirmDialog(null, 
                "¿Deseas comprar " + getNombre() + " por $" + costo + "?", 
                "Compra de propiedad", 
                JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                if (jugador.getDinero() >= costo) {
                    jugador.pagar(costo);
                    propietario = jugador;
                    jugador.agregarPropiedad(this);  
                    JOptionPane.showMessageDialog(null, "Has hecho la Compra " + getNombre() + ".");
                } else {
                    JOptionPane.showMessageDialog(null, "No tienes suficiente dinero para comprar " + getNombre() + ".");
                }
            }
        } else if (!propietario.equals(jugador)) {
            int alquiler = calcularAlquiler();
            jugador.pagar(alquiler);
            propietario.recibirDinero(alquiler);
            JOptionPane.showMessageDialog(null, "Has pagado $" + alquiler + " de alquiler a " + propietario.getNombre() + ".");
        }
    }

    public int getCosto() {
        return costo;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    private int calcularAlquiler() {
        
        return (int) (costo * 0.2); 
    }
}

class Impuestos extends Casilla {
    private int monto;

    public Impuestos(String nombre, int monto) {
        super(nombre);
        this.monto = monto;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        System.out.println(jugador.getNombre() + " paga impuestos de " + monto);
        jugador.pagar(monto);
    }
}

class Carcel extends Casilla {
    public Carcel(String nombre, int posicion) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        System.out.println(jugador.getNombre() + " está de visita en la cárcel");
        
    }
}

class Salida extends Casilla {
    private int bonificacion;

    public Salida(String nombre, int bonificacion) {
        super(nombre);
        this.bonificacion = bonificacion;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.recibirDinero(bonificacion);
        System.out.println(jugador.getNombre() + " pasa por " + nombre + " y recibe " + bonificacion);
    }
}

class Estacion extends Casilla {
    private int costo;

    public Estacion(String nombre, int costo) {
        super(nombre);
        this.costo = costo;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        System.out.println(jugador.getNombre() + " ha aterrizado en " + nombre);
        
    }
}

class ServicioPublico extends Casilla {
    private int costo;

    public ServicioPublico(String nombre, int costo) {
        super(nombre);
        this.costo = costo;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        System.out.println(jugador.getNombre() + " ha aterrizado en " + nombre);
        
    }
}

class CajaComunidad extends Casilla {
    public CajaComunidad(String nombre) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        Tarjeta tarjeta = juego.sacarTarjetaCajaComunidad();
        tarjeta.ejecutar(jugador, juego);
        System.out.println("Caja de Comunidad: " + tarjeta.getDescripcion());
    }
}

class Suerte extends Casilla {
    public Suerte(String nombre) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        Tarjeta tarjeta = juego.sacarTarjetaSuerte();
        tarjeta.ejecutar(jugador, juego);
        System.out.println("Suerte: " + tarjeta.getDescripcion());
    }
}

class Libre extends Casilla {
    public Libre(String nombre) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        System.out.println(jugador.getNombre() + " está en el Estacionamiento Libre. No pasa nada.");
    }
}
