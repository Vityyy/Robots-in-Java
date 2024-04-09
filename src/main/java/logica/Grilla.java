package logica;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Grilla{
    private final int n_filas;
    private final int n_columnas;
    private final ArrayList<ArrayList<Object>> matriz = new ArrayList<ArrayList<Object>>();

    public Grilla(int n_filas, int n_columnas) {
        this.n_filas = n_filas;
        this.n_columnas = n_columnas;
        grillaEnBlanco();
    }
    private void grillaEnBlanco(){
        for (int i = 0; i < this.n_filas; i++) {
            ArrayList<Object> fila = new ArrayList<Object>();
            for (int j = 0; j < this.n_columnas; j++) {
                fila.add(null);
            }
            this.matriz.add(fila);
        }
    }
    public void actualizarGrilla(Map<Object, int[]> posiciones_objetos) {
        this.grillaEnBlanco();
        for (Map.Entry<Object,int[]> entry : posiciones_objetos.entrySet()) {
            Object objeto = entry.getKey();
            int[] posicion = entry.getValue();
            this.matriz.get(posicion[0]).set(posicion[1], objeto);
        }
    }
}