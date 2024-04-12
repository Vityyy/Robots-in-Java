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
    private int tpsSeguros;
    private Grilla grilla;

    public Sistema() {
        this.Score = 0;
        this.tpsSeguros = 2;
        this.nivel = 1;
        this.jugador = Jugador();
        crearEnemigos(nivel, n_fil, n_col);
        this.grilla = Grilla(n_fil, n_col, this.jugador, this.enemigos);
    }

    private void crearEnemigos(int nivel, int n_fil, int n_col) {
        var enemigos = new ArrayList<Enemigo>();
        int maxEnemigos = (n_fil * n_col * nivel) / 40;
        int cantSimples = (maxEnemigos * 3) / 4;
        int cantComplejos = (maxEnemigos) / 4;

        for(int i = 0; i <= cantSimples; i++) {
            var enemigo = new RobotSimple();
            enemigos.add(enemigo);
        }
        for(int i=0; i<= cantComplejos;i++) {
            var enemigo = new RobotComplejo();
            enemigos.add(enemigo);
        }
        this.enemigos = enemigos;
    }

    public void jugarTurno(int[] coordenadas){
        this.jugador.moverse(coordenadas,this.grilla);
        for (Enemigo enemigo : this.enemigos){
            enemigo.moverse(this.grilla);
        }
    }
    public int getScore() {
        return Score;
    }
    public int getTpsSeguros() {
        return Tps_seguros;
    }
}
