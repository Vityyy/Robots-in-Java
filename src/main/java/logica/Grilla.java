package logica;

import java.util.ArrayList;

public class Grilla<T>{
    private final int n_filas;
    private final int n_columnas;
    private int[] posicion_jugador;
    private int[] posicion_robots;
    private ArrayList<ArrayList<T>> matriz;


    public Grilla(int n_filas, int n_columnas, int[] posicion_robots) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        this.posicion_jugador = new int[]{n_filas/2 , n_columnas/2};
        this.posicion_robots = posicion_robots;
        crearGrilla();
    }

    private void crearGrilla(){
        this.matriz = new ArrayList<ArrayList<T>>();

        for (int i = 0; i < n_filas; i++) {
            ArrayList<T> fila = new ArrayList<T>();
            for (int j = 0; j < n_columnas; j++) {
                fila.add(null);
            }
            matriz.add(fila);
        }
    }
    public void otorgarPosicion(Robot robot){
    }
}
