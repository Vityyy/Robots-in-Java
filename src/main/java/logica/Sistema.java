package logica;

import java.util.*;

/**
 * Clase que hace de administrador del juego
*/

public class Sistema {
    private static final int TPS_INICIALES = 2;
    private final int n_fil;
    private final int n_col;
    private final Jugador jugador;
    private final int nivel;
    private int score;
    private int tps_seguros;
    private final Grilla grilla;
    private final ControladorJugador Controlador_J;


    /**
     * Constructor
     */
    public Sistema(int score, int nivel, int filas, int columnas) {
        this.score = score;
        this.tps_seguros = TPS_INICIALES;
        this.nivel = nivel;
        this.n_fil = filas;
        this.n_col = columnas;
        this.jugador = new Jugador(n_fil, n_col);
        this.grilla = new Grilla(n_fil, n_col, CreadorDeRobots.crearRobots(nivel, n_fil, n_col), jugador);
        this.Controlador_J = new ControladorJugador(jugador);
    }

    /**
     * Constructor
     */
    public Sistema(int score, int nivel, int filas, int columnas, int tps_seguros) {
        this.score = score;
        this.tps_seguros = tps_seguros;
        this.nivel = nivel;
        this.n_fil = filas;
        this.n_col = columnas;
        this.jugador = new Jugador(this.n_fil, this.n_col);
        this.grilla = new Grilla(n_fil, n_col, CreadorDeRobots.crearRobots(nivel, n_fil, n_col),jugador);
        this.Controlador_J = new ControladorJugador(jugador);
    }

    public boolean turno(int[] coordenadas, int movimiento){
        if (this.Controlador_J.validarCoordenadas(coordenadas, this.n_fil, this.n_col)) {
            Controlador_J.elegirMovimiento(coordenadas, movimiento,this, this.n_fil, this.n_col);
            return this.grilla.actualizarGrilla(this, this.jugador);
        }
        return false;
    }
    /**
     * Actualiza las posiciones de los robots y del jugador y las devuelve
     * @return ArrayList</Object>
     */
    public ArrayList<Object> estadoJuego(){
        Map<Robot, int[]> posiciones_robots = this.grilla.getPosicionesRobots();
        int[] posicion_jugador = jugador.getPosicionJugador();
        ArrayList<Object> resultado = new ArrayList<>();
        resultado.add(posicion_jugador);
        resultado.add(posiciones_robots);
        return resultado;
    }

    /**
     * Evalúa si no quedan robots en la Grilla (ganar el juego)
     * @return boolean
     */
    public boolean juegoGanado(){
        var posiciones_robots = this.grilla.getPosicionesRobots();
        for (Robot robot : posiciones_robots.keySet()){
            if (robot.getFuncional()){
                return false;
            }
        }
        return true;
    }

    public int getNivel() {return nivel;}
    public int getScore() {return score;}
    public int getTpsSeguros() {return tps_seguros;}
    public boolean jugadorEstaVivo() {return jugador.getVivo();}
    public int[] getPosicionJugador(){return jugador.getPosicionJugador();}
    public void aumentarScore(int score_plus) {this.score += score_plus;}
    public void aumentarTpsSeguros() {this.tps_seguros += 1;}
    public void reducirTpsSeguros() {this.tps_seguros -= 1;}
    public int[] getDimension(){return new int[]{n_fil,n_col};}
}