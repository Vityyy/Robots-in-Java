package logica;

import java.util.ArrayList;

public class Grilla<T>{
    private final int n_filas;
    private final int n_columnas;

    //private ArrayList<Pair<int, int>> robots;


    public Grilla(int n_filas, int n_columnas) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        crearGrilla();
    }

    private void crearGrilla(){
        var arr = new ArrayList<ArrayList<T>>();

        for (int i = 0; i < n_filas; i++) {
            var fila = new ArrayList<T>();
            for (int j = 0; j < n_columnas; j++) {
                fila.add(null);
            }
        }
    }




}
