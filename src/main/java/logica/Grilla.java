package logica;

import java.util.*;

public class Grilla{
    private final int n_filas;
    private final int n_columnas;
    private int[] coordenadas_jugador;
    private Map<Enemigo, int[]> posiciones = new HashMap<>();
    public Grilla(int n_filas, int n_columnas, Enemigo[] enemigos) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        this.coordenadas_jugador = new int[]{n_filas/2, n_columnas/2};
        inicializarGrilla(enemigos);
    }
    private void inicializarGrilla(Enemigo[] enemigos){
        Set<int[]> set = new HashSet<>();
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
        Map<Enemigo, int[]> nuevas_posiciones = new HashMap<>();
        Map<String, Enemigo> posibles_colisiones = new HashMap<>();

        for(Enemigo enemigo : this.posiciones.keySet()){
            int[] posicion_nueva = enemigo.moverse(this);
            String key_posicion_nueva = Arrays.toString(posicion_nueva);

            if (this.coordenadas_jugador[0] == posicion_nueva[0] && this.coordenadas_jugador[1] == posicion_nueva[1]){
                this.posiciones = nuevas_posiciones;
                return true;
            }
            if (posibles_colisiones.containsKey(key_posicion_nueva)){
                enemigo.setNoFuncional();
                posibles_colisiones.get(key_posicion_nueva).setNoFuncional();
            }
            nuevas_posiciones.put(enemigo, posicion_nueva);
            posibles_colisiones.put(key_posicion_nueva, enemigo);
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