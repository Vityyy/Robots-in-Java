package logica;

import java.util.ArrayList;


//HACER CONSTANTES LOS NÚMEROS DE MIERDA


public class Sistema {

    private final int n_fil = 45;
    private final int n_col = 45;
    private Jugador jugador;
    private ArrayList<Enemigo> enemigos;
    private int nivel;
    private int Score;
    private int Tps_seguros;
    private Grilla grilla;

    public Sistema() {
        this.Score = 0;
        this.Tps_seguros = 2;
        this.nivel = 1;
        this.jugador = Jugador();
        crear_enemigos(nivel, n_fil, n_col);
        this.grilla = Grilla(n_fil, n_col, this.jugador, this.enemigos);
    }

    private void crear_enemigos(int nivel, int n_fil, int n_col) {
        var enemigos = new ArrayList<Enemigo>();
        int max_enemigos = (n_fil * n_col * nivel) / 40;
        int cant_simples = (max_enemigos * 3) / 4;
        int cant_complejos = (max_enemigos) / 4;

        for(int i = 0; i <= cant_simples; i++) {
            var enemigo = new RobotSimple();
            enemigos.add(enemigo);
        }
        for(int i=0; i<= cant_complejos;i++) {
            var enemigo = new RobotComplejo();
            enemigos.add(enemigo);
        }
        this.enemigos = enemigos;
    }


    public int getN_fil() {
        return n_fil;
    }
    public int getN_col() {
        return n_col;
    }
    public int getScore() {
        return Score;
    }
    public int getTps_seguros() {
        return Tps_seguros;
    }
}
