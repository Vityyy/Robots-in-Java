package logica;

public class ControladorJugador {
    private final Jugador jugador;
    private static final int MOVIMIENTO_NORMAL = 1;
    private static final int MOVIMIENTO_ALEATORIO = 2;
    private static final int MOVIMIENTO_AL_SEGURO = 3;

    public ControladorJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * Valida si las coordenadas ingresadas son válidas
     *
     * @param coordenadas , n_fil, n_col
     * @return boolean
     */
    public boolean validarCoordenadas(int[] coordenadas, int n_fil, int n_col) {
        return coordenadas == null || (coordenadas[0] >= 0 && coordenadas[0] < n_fil && coordenadas[1] >= 0 && coordenadas[1] < n_col);
    }

    public void elegirMovimiento(int[] coordenadas, int movimiento, Sistema sistema, int n_fil, int n_col){
        if (movimiento == MOVIMIENTO_NORMAL) {
            jugarTurno(coordenadas);
        } else if (movimiento == MOVIMIENTO_ALEATORIO) {
            jugarTpAleatorio(n_fil, n_col);
        } else if (movimiento == MOVIMIENTO_AL_SEGURO) {
            sistema.reducirTpsSeguros();
            jugarTpSeguro(coordenadas);
        }
    }

    /**
     * Permite al jugador moverse por la Grilla.
     *
     * @param coordenadas posicion a moverse
     */
    private void jugarTurno(int[] coordenadas) {
        this.jugador.moverse(coordenadas);
    }

    /**
     * Permite al jugador moverse en la Grilla de forma aleatoria.
     *
     */
    private void jugarTpAleatorio(int n_fil, int n_col) {
        int[] coordenadas = new int[]{(int) (Math.random() * (n_fil - 1)), (int) (Math.random() * (n_col - 1))};
        this.jugador.setPosicionJugador(coordenadas);
        this.jugador.moverse(coordenadas);
    }

    /**
     * Permite al jugador moverse en la grilla de forma segura.
     *
     * @param coordenadas posicion a moverse
     */
    private void jugarTpSeguro(int[] coordenadas) {
        this.jugador.setPosicionJugador(coordenadas);
        this.jugador.moverse(coordenadas);
    }
}