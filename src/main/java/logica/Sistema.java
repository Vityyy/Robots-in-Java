package logica;


//HACER CONSTANTES LOS NÚMEROS DE MIERDA

import java.util.ArrayList;
import java.util.Map;

public class Sistema {
    private final int n_fil = 40;
    private final int n_col = 40;
    private final Jugador jugador;
    private Enemigo[] enemigos;
    private int nivel;
    private int Score;
    private int tps_seguros;
    private final Grilla grilla;

    public Sistema() {
        this.Score = 0;
        this.tps_seguros = 2;
        this.nivel = 1;
        this.jugador = new Jugador();
        crearEnemigos(nivel);
        this.grilla = new Grilla(n_fil, n_col, this.enemigos);
    }

    private void crearEnemigos(int nivel) {
        int maxEnemigos = (this.n_fil * this.n_col * nivel) / 40;
        Enemigo[] enemigos = new Enemigo[maxEnemigos];
        int cantSimples = (maxEnemigos * 3) / 4;

        for(int i = 0; i < cantSimples; i++) {
            var enemigo = new RobotSimple();
            enemigos[i] = enemigo;
        }
        for(int j = cantSimples; j< maxEnemigos; j++) {
            var enemigo = new RobotComplejo();
            enemigos[j] = enemigo;
        }
        this.enemigos = enemigos;
    }
    public boolean jugarTurno(int[] coordenadas){
        this.jugador.moverse(coordenadas, this.grilla);
        return this.grilla.actualizarGrilla();
    }
    public boolean jugarTpAleatorio(){
        int[] coordenadas = new int[]{(int) (Math.random() * n_fil) ,(int) (Math.random() *n_col)};
        this.grilla.setPosicionJugador(coordenadas);
        return jugarTurno(new int[]{-1,-1});
    }
    public boolean JugarTpSeguro(int[] coordenadas){
        grilla.setPosicionJugador(coordenadas);
        tps_seguros-=1;
        return jugarTurno(new int[]{-1,-1});
    }
    public ArrayList<Object> estadoJuego(){
        Map<Enemigo, int[]> posiciones_enemigos = this.grilla.getPosicionesEnemigos();
        int[] posicion_jugador = this.grilla.getPosicionJugador();
        ArrayList<Object> resultado = new ArrayList<>();
        resultado.add(posicion_jugador);
        resultado.add(posiciones_enemigos);
        return resultado;
    }

    public int getNivel() {
        return nivel;
    }

    public int getScore() {
        return Score;
    }

    public int getTpsSeguros() {
        return tps_seguros;
    }
}

