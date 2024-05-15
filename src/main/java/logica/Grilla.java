package logica;
import java.util.*;

/**
 * Clase que modela la grilla del juego y la maneja de diversas formas
 */

public class Grilla{
    private final static int PUNTOS_POR_COLISION = 20;
    private final int n_filas;
    private final int n_columnas;
    private Map<Robot, int[]> posiciones = new HashMap<>();

    /**
     * Constructor
     */
    public Grilla(int n_filas, int n_columnas, Robot[] robots, Jugador jugador) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        inicializarGrilla(robots, jugador);
    }

    /**
     * Inicializa la grilla con las posiciones de los robots.
     * Asegura que no terminen en la misma posicion
     * @param robots lista de robots creados
     */
    private void inicializarGrilla(Robot[] robots, Jugador jugador){
        Set<String> set = new HashSet<>();
        int[] coordenadas_jugador = jugador.getPosicionJugador();

        String posicion_jugador = Arrays.toString(coordenadas_jugador);
        set.add(posicion_jugador);
        for (Robot robot : robots) {
            int[] posicion = robot.spawn(n_filas, n_columnas);
            String posicion_robot = Arrays.toString(posicion);

            while (set.contains(posicion_robot)) {
                posicion = robot.spawn(n_filas, n_columnas);
                posicion_robot = Arrays.toString(posicion);
            }
            set.add(posicion_robot);
            this.posiciones.put(robot,posicion);
        }
    }

    /**
     * Actualiza el estado actual de juego según el movimiento del jugador
     * Trata los casos de colisiones y actualiza el score
     * @param sistema se usa para actualizar el score
     * @return boolean
     */
    public boolean actualizarGrilla(Sistema sistema, Jugador jugador){
        Map<Robot, int[]> nuevas_posiciones = new HashMap<>();
        Map<String, Robot> posibles_colisiones = new HashMap<>();
        boolean end_game = false;
        int[] coordenadas_jugador = jugador.getPosicionJugador();

        for(Robot robot : this.posiciones.keySet()){
            int[] posicion_nueva = robot.moverse(coordenadas_jugador);
            String key_posicion_nueva = Arrays.toString(posicion_nueva);

            if (posibles_colisiones.containsKey(key_posicion_nueva)) {
                if (robot.getFuncional()) {
                    sistema.aumentarScore(PUNTOS_POR_COLISION);
                    robot.setNoFuncional();
                }
                if (posibles_colisiones.get(key_posicion_nueva).getFuncional()) {
                    posibles_colisiones.get(key_posicion_nueva).setNoFuncional();
                    sistema.aumentarScore(PUNTOS_POR_COLISION);
                }
            }
            nuevas_posiciones.put(robot, posicion_nueva);
            posibles_colisiones.put(key_posicion_nueva, robot);
            if (coordenadas_jugador[0] == posicion_nueva[0] && coordenadas_jugador[1] == posicion_nueva[1]){
                jugador.setNoVivo();
                end_game = true;
            }
        }
        this.posiciones = nuevas_posiciones;
        return end_game;
    }
    public Map<Robot, int[]> getPosicionesRobots() {return this.posiciones;}
}