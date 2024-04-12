package logica;


//HACER CONSTANTES LOS NÚMEROS DE MIERDA


public class Sistema {
    private final int n_fil = 45;
    private final int n_col = 45;
    private Jugador jugador;
    private Enemigo[] enemigos;
    private int nivel;
    private int Score;
    private int tpsSeguros;
    private Grilla grilla;

    public Sistema() {
        this.Score = 0;
        this.tpsSeguros = 2;
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

    public void jugarTurno(int[] coordenadas){
        this.jugador.moverse(coordenadas, this.grilla);
        boolean juego_terminado = this.grilla.actualizarGrilla();
        if (juego_terminado){
            finalizarPartida();
        }
    }

    private void finalizarPartida() {
    }
}
