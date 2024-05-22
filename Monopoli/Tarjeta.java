
package Monopoli;

abstract class Tarjeta {
    private String descripcion;

    public Tarjeta(String descripcion) {
        this.descripcion = descripcion;
    }

    public abstract void ejecutar(Jugador jugador, Juego juego);

    public String getDescripcion() {
        return descripcion;
    }
}

class DineroTarjeta extends Tarjeta {
    private int monto;

    public DineroTarjeta(String descripcion, int monto) {
        super(descripcion);
        this.monto = monto;
    }

    @Override
    public void ejecutar(Jugador jugador, Juego juego) {
        jugador.recibirDinero(monto);
        System.out.println(getDescripcion());
    }
}

class MoverTarjeta extends Tarjeta {
    private int posiciones;

    public MoverTarjeta(String descripcion, int posiciones) {
        super(descripcion);
        this.posiciones = posiciones;
    }

    @Override
    public void ejecutar(Jugador jugador, Juego juego) {
        jugador.moverFicha(posiciones);
        Casilla casillaActual = juego.getTablero().getCasilla(jugador.getPosicion());
        casillaActual.ejecutarAccion(jugador, juego);
        System.out.println(getDescripcion());
    }
}

class CarcelTarjeta extends Tarjeta {
    public CarcelTarjeta(String descripcion) {
        super(descripcion);
    }

    @Override
    public void ejecutar(Jugador jugador, Juego juego) {
        jugador.enviarACarcel();
        System.out.println(getDescripcion());
    }
}