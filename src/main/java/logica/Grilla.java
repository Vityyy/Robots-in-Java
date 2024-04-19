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
    public Grilla(int n_filas, int n_columnas, Enemigo[] enemigos) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        this.coordenadas_jugador = new int[]{n_filas/2, n_columnas/2};
        inicializarGrilla(enemigos);
    }
    private void inicializarGrilla(Enemigo[] enemigos){
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
    public boolean actualizarGrilla(){
        Map<Enemigo, int[]> nuevas_posiciones = new HashMap<Enemigo, int[]>();
        Map<int[], Enemigo> posibles_colisiones = new HashMap<int[], Enemigo>();

        for(Enemigo enemigo : this.posiciones.keySet()){
            int[] posicion_nueva = enemigo.moverse(this);

            if (this.coordenadas_jugador == posicion_nueva){
                return true;
            }
            if (posibles_colisiones.containsKey(posicion_nueva)){
                enemigo.setNoFuncional();
                posibles_colisiones.get(posicion_nueva).setNoFuncional();
            }
            nuevas_posiciones.put(enemigo,posicion_nueva);
            posibles_colisiones.put(posicion_nueva, enemigo);
        }
        this.posiciones = nuevas_posiciones;
        return false;
    }
    public int[] getPosicionEnemigo(Enemigo enemigo) {
        return this.posiciones.get(enemigo);
    }
    public Map<Enemigo, int[]> getPosicionesEnemigos() {return this.posiciones;}
    public void setPosicionJugador(int[] coordenadas){
        this.coordenadas_jugador = coordenadas;
    }
    public int[] getPosicionJugador() {
        return this.coordenadas_jugador;
    }
}