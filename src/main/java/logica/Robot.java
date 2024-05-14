package logica;

/**
 * Superclase que modela un robot default
 */

public class Robot {
    private boolean funcional = true;
    private int[] posicion_actual;
    public int[] spawn(int n_fil, int n_col){
        this.posicion_actual = new int[]{(int) (Math.random() * (n_fil - 1)), (int) (Math.random() * (n_col - 1))};
        return posicion_actual;
    }
    public int[] moverse(int[] coordenadas_jugador) {
        if (!this.funcional) {
            return this.posicion_actual;
        }
        int[] nueva_posicion = new int[]{this.posicion_actual[0], this.posicion_actual[1]};
        nueva_posicion[0] += Integer.compare(coordenadas_jugador[0], this.posicion_actual[0]);
        nueva_posicion[1] += Integer.compare(coordenadas_jugador[1], this.posicion_actual[1]);

        this.posicion_actual = nueva_posicion;

        return nueva_posicion;
    }
    public void setNoFuncional(){this.funcional = false;}
    public boolean getFuncional(){return this.funcional;}
}
