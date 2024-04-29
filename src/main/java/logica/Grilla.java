package logica;
import java.util.*;

/**
 * Clase que modela la grilla del juego y la maneja de diversas formas
 */

public class Grilla{
    private final static int PUNTOS_POR_COLISION = 20;
    private final int n_filas;
    private final int n_columnas;
    private int[] coordenadas_jugador;
    private Map<Enemigo, int[]> posiciones = new HashMap<>();

    /**
     * Constructor
     */
    public Grilla(int n_filas, int n_columnas, Enemigo[] enemigos) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        this.coordenadas_jugador = new int[]{n_filas/2, n_columnas/2};
        inicializarGrilla(enemigos);
    }

    /**
     * Inicializa la grilla con las posiciones de los enemigos.
     * Asegura que no terminen en la misma posicion
     * @param enemigos lista de enemigos creados
     */
    private void inicializarGrilla(Enemigo[] enemigos){
        Set<String> set = new HashSet<>();
        String posicion_jugador = Arrays.toString(coordenadas_jugador);
        set.add(posicion_jugador);
        for (Enemigo enemigo : enemigos) {
            int[] posicion = new int[]{(int) (Math.random() * (n_filas-1)), (int) (Math.random() * (n_columnas-1))};
            String posicion_robot = Arrays.toString(posicion);
            while (set.contains(posicion_robot)) {
                posicion[0] = (int) (Math.random() * (n_filas-1));
                posicion[1] = (int) (Math.random() * (n_columnas-1));
                }
            set.add(posicion_robot);
            this.posiciones.put(enemigo,posicion);
        }
    }

    /**
     * Actualiza el estado actual de juego según el movimiento del jugador
     * Trata los casos de colisiones y actualiza el score
     * @param sistema se usa para actualizar el score
     * @return boolean
     */
    public boolean actualizarGrilla(Sistema sistema){
        Map<Enemigo, int[]> nuevas_posiciones = new HashMap<>();
        Map<String, Enemigo> posibles_colisiones = new HashMap<>();
        boolean end_game = false;

        for(Enemigo enemigo : this.posiciones.keySet()){
            int[] posicion_nueva = enemigo.moverse(this);
            String key_posicion_nueva = Arrays.toString(posicion_nueva);

            if (posibles_colisiones.containsKey(key_posicion_nueva)) {
                if (enemigo.getFuncional()) {
                    sistema.aumentarScore(PUNTOS_POR_COLISION);
                    enemigo.setNoFuncional();
                }
                if (posibles_colisiones.get(key_posicion_nueva).getFuncional()) {
                    posibles_colisiones.get(key_posicion_nueva).setNoFuncional();
                    sistema.aumentarScore(PUNTOS_POR_COLISION);
                }
            }
            nuevas_posiciones.put(enemigo, posicion_nueva);
            posibles_colisiones.put(key_posicion_nueva, enemigo);

            if (this.coordenadas_jugador[0] == posicion_nueva[0] && this.coordenadas_jugador[1] == posicion_nueva[1]){
                sistema.setJugadorNoVivo();
                end_game = true;
            }
        }
        this.posiciones = nuevas_posiciones;
        return end_game;
    }

    public int[] getPosicionEnemigo(Enemigo enemigo) {return this.posiciones.get(enemigo);}
    public Map<Enemigo, int[]> getPosicionesEnemigos() {return this.posiciones;}
    public void setPosicionJugador(int[] coordenadas){this.coordenadas_jugador = coordenadas;}
    public int[] getPosicionJugador() {return this.coordenadas_jugador;}
}