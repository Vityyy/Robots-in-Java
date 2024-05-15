package logica;

/**
 * Clase que modela un jugador
 */

public class Jugador {
    private boolean vivo;
    private int[] posicion_actual;


    public Jugador (int n_fil, int n_col){
        this.vivo = true;
        this.posicion_actual = new int[]{n_fil / 2, n_col / 2};
    }

    /**
     * Mueve al jugador en la dirección deseada
     * @param coordenadas coordenadas a las que se moverá
     */
    public void moverse(int[] coordenadas) {
        int[] nueva_posicion = {posicion_actual[0], posicion_actual[1]};
        nueva_posicion[0] += Integer.compare(coordenadas[0], posicion_actual[0]);
        nueva_posicion[1] += Integer.compare(coordenadas[1], posicion_actual[1]);
        this.posicion_actual = nueva_posicion;
    }

    public boolean getVivo() {return vivo;}
    public void setNoVivo() {this.vivo = false;}
    public void setPosicionJugador(int[] coordenadas){this.posicion_actual = coordenadas;}
    public int[] getPosicionJugador() {return this.posicion_actual;}

}