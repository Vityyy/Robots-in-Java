package logica;

/**
 * Interfaz que define el comportamiento de los enemigos
 */

public interface Enemigo{
    /**
     * Mueve al enemigo en la dirección del jugador
     * @param grilla Grilla
     * @return int[]
     */
    int[] moverse(Grilla grilla);
    void setNoFuncional();
    boolean getFuncional();
}
