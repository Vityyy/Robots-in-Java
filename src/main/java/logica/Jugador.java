package logica;

/**
 * Clase que modela un jugador
 */

public class Jugador {
    private boolean vivo = true;

    /**
     * Mueve al jugador en la dirección deseada
     * @param coordenadas
     * @param Grilla
     */
    public void moverse(int[] coordenadas, Grilla Grilla) {
        int[] posicion_actual = Grilla.getPosicionJugador();
        int[] nueva_posicion = {posicion_actual[0], posicion_actual[1]};
        nueva_posicion[0] += Integer.compare(coordenadas[0], posicion_actual[0]);
        nueva_posicion[1] += Integer.compare(coordenadas[1], posicion_actual[1]);
        Grilla.setPosicionJugador(nueva_posicion);
    }
    public boolean getVivo() {return vivo;}
    public void setNoVivo() {this.vivo = false;}
}