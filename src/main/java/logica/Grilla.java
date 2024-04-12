package logica;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Grilla{
    private final int n_filas;
    private final int n_columnas;
    private int[] coordenadas_jugador;
    private Map<Enemigo, int[]> posiciones = new HashMap<Enemigo, int[]>();


    public Grilla(int n_filas, int n_columnas, Jugador jugador, Enemigo[] enemigos) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        this.coordenadasJugador = new int[]{n_filas/2, n_columnas/2};
        grillaEnBlanco(enemigos);
    }
    private void grillaEnBlanco(Enemigo[] enemigos){
        Set<int[]> set = new HashSet<int[]>();
        set.add(this.coordenadas_jugador);
        for (Enemigo enemigo : enemigos) {
            int[] posicion = new int[]{(int) (Math.random() * n_filas), (int) (Math.random() * n_columnas)};

            while (set.contains(posicion)) {
                posicion[0] = (int) (Math.random() * n_filas);
                posicion[1] = (int) (Math.random() * n_columnas);
                }

            set.add(posicion);
            this.posiciones.put(enemigo,posicion);
        }
    }
    public void setPosicionEnemigo(Enemigo enemigo, int[] coordenada) {
        this.posiciones.put(enemigo,coordenada);
    }
    public int[] getPosicionEnemigo(Enemigo enemigo) {
        return this.posiciones.get(enemigo);
    }
    public void setPosicionJugador(int[] coordenadas){
        this.coordenadas_jugador=coordenadas;
    }
    public int[] getPosicionJugador() {
        return this.coordenadas_jugador;
    }
}