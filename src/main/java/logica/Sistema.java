package logica;


//HACER CONSTANTES LOS NÚMEROS DE MIERDA

import java.util.ArrayList;
import java.util.Map;

public class Sistema {
    private final int n_fil = 5;
    private final int n_col = 5;
    private final Jugador jugador;
    private final int nivel;
    private int score;
    private int tps_seguros;
    private final Grilla grilla;

    public Sistema(int score,int nivel) {
        this.score = score;
        this.tps_seguros = 2;
        this.nivel = nivel;
        this.jugador = new Jugador();
        this.grilla = new Grilla(n_fil, n_col,crearEnemigos(nivel));
    }
    private Enemigo[] crearEnemigos(int nivel) {
        int maxEnemigos = (this.n_fil * this.n_col * nivel) / 60;
        if (maxEnemigos < 2) {
            maxEnemigos = 2;
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
    public boolean jugarTurno(int[] coordenadas){
        this.jugador.moverse(coordenadas, this.grilla);
        return this.grilla.actualizarGrilla(this);
    }
    public boolean jugarTpAleatorio(){
        int[] coordenadas = new int[]{(int) (Math.random() * (n_fil-1)) ,(int) (Math.random() *(n_col-1))};
        this.grilla.setPosicionJugador(coordenadas);
        return jugarTurno(grilla.getPosicionJugador());
    }
    public boolean JugarTpSeguro(int[] coordenadas){
        grilla.setPosicionJugador(coordenadas);
        tps_seguros -= 1;
        return jugarTurno(grilla.getPosicionJugador());
    }
    public ArrayList<Object> estadoJuego(){
        Map<Enemigo, int[]> posiciones_enemigos = this.grilla.getPosicionesEnemigos();
        int[] posicion_jugador = this.grilla.getPosicionJugador();
        ArrayList<Object> resultado = new ArrayList<>();
        resultado.add(posicion_jugador);
        resultado.add(posiciones_enemigos);
        return resultado;
    }
    public boolean juegoGanado(){
        var posiciones_enemigos = this.grilla.getPosicionesEnemigos();
        for (Enemigo enemigo : posiciones_enemigos.keySet()){
            if (enemigo.getFuncional()){
                return false;
            }
        }
        return true;
    }
    public int getNivel() {
        return nivel;
    }
    public int getScore() {
        return score;
    }
    public int getTpsSeguros() {
        return tps_seguros;
    }
    public boolean jugadorEstaVivo() {return jugador.getVivo();}
    public void setJugadorNoVivo() {jugador.setNoVivo();}
    public int[] getPosicionJugador(){return grilla.getPosicionJugador();}
    public void aumentarScore(int score_plus) {this.score += score_plus;}
}

