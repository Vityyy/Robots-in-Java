package logica;

import java.util.*;

/**
 * Clase que hace de administrador del juego
*/

public class Sistema {
    private static final int TPS_INICIALES = 2;
    private int n_fil;
    private int n_col;
    private final Jugador jugador;
    private final int nivel;
    private int score;
    private int tps_seguros;
    private final Grilla grilla;

    /**
     * Constructor
     */
    public Sistema(int score, int nivel, int filas, int columnas) {
        this.score = score;
        this.tps_seguros = TPS_INICIALES;
        this.nivel = nivel;
        this.n_fil = filas;
        this.n_col = columnas;
        this.jugador = new Jugador();
        this.grilla = new Grilla(n_fil, n_col,crearEnemigos(nivel));
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
        this.jugador = new Jugador();
        this.grilla = new Grilla(n_fil, n_col,crearEnemigos(nivel));
    }

    /**
     * Inicializa los enemigos del juego según el nivel en el que se encuentra el jugador.
     * @param nivel nivel actual
     * @return Enemigo[]
     */
    private Enemigo[] crearEnemigos(int nivel) {
        int maxEnemigos = (this.n_fil * this.n_col * nivel) / 60;
        if (maxEnemigos < 2) {
            maxEnemigos = 1 + nivel;
        }
        Enemigo[] enemigos = new Enemigo[maxEnemigos];
        int cantSimples = (maxEnemigos * 2) / 4;
        for(int i = 0; i < cantSimples; i++) {
            var enemigo = new RobotSimple();
            enemigos[i] = enemigo;
        }
        for(int j = cantSimples; j< maxEnemigos; j++) {
            var enemigo = new RobotComplejo();
            enemigos[j] = enemigo;
        }
        return enemigos;
    }
    /**
     * Permite al jugador moverse por la Grilla.
     * @param coordenadas posicion a moverse
     * @return boolean
     */
    public boolean jugarTurno(int[] coordenadas){
        if (coordenadas[0]>=0 && coordenadas[0] < n_fil && coordenadas[1] >= 0 && coordenadas[1] < n_col) {
            this.jugador.moverse(coordenadas, this.grilla);
            return this.grilla.actualizarGrilla(this);
        }
        return false;
    }

    /**
     * Permite al jugador moverse en la Grilla de forma aleatoria.
     * @return boolean
     */
    public boolean jugarTpAleatorio(){
        int[] coordenadas = new int[]{(int) (Math.random() * (n_fil-1)) ,(int) (Math.random() *(n_col-1))};
        this.grilla.setPosicionJugador(coordenadas);
        return jugarTurno(grilla.getPosicionJugador());
    }

    /**
     * Permite al jugador moverse en la grilla de forma segura.
     * @param coordenadas posicion a moverse
     * @return boolean
     */
    public boolean JugarTpSeguro(int[] coordenadas){
        grilla.setPosicionJugador(coordenadas);
        tps_seguros -= 1;
        return jugarTurno(grilla.getPosicionJugador());
    }

    /**
     * Actualiza las posiciones de los enemigos y del jugador y las devuelve
     * @return ArrayList</Object>
     */
    public ArrayList<Object> estadoJuego(){
        Map<Enemigo, int[]> posiciones_enemigos = this.grilla.getPosicionesEnemigos();
        int[] posicion_jugador = this.grilla.getPosicionJugador();
        ArrayList<Object> resultado = new ArrayList<>();
        resultado.add(posicion_jugador);
        resultado.add(posiciones_enemigos);
        return resultado;
    }

    /**
     * Evalúa si no quedan enemigos en la Grilla (ganar el juego)
     * @return boolean
     */
    public boolean juegoGanado(){
        var posiciones_enemigos = this.grilla.getPosicionesEnemigos();
        for (Enemigo enemigo : posiciones_enemigos.keySet()){
            if (enemigo.getFuncional()){
                return false;
            }
        }
        return true;
    }

    public int getNivel() {return nivel;}
    public int getScore() {return score;}
    public int getTpsSeguros() {return tps_seguros;}
    public boolean jugadorEstaVivo() {return jugador.getVivo();}
    public void setJugadorNoVivo() {jugador.setNoVivo();}
    public int[] getPosicionJugador(){return grilla.getPosicionJugador();}
    public void aumentarScore(int score_plus) {this.score += score_plus;}
    public void aumentarTpsSeguros() {this.tps_seguros += 1;}
    public int[] getDimension(){return new int[]{n_fil,n_col};}
}

