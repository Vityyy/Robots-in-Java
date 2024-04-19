package interfaz;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Tablero {

    private int ancho_ventana;
    private int alto_ventana;
    private int ancho_canvas;
    private int alto_canvas;
    private int filas;
    private int columnas;
    private int casillas_ancho;
    private int casilla_alto;

    public Tablero(int ancho_ventana, int alto_ventana, int ancho_canvas, int alto_canvas, int filas, int columnas, int casillas_ancho, int casilla_alto) {
        this.ancho_ventana = ancho_ventana;
        this.alto_ventana = alto_ventana;
        this.ancho_canvas = ancho_canvas;
        this.alto_canvas = alto_canvas;
        this.filas = filas;
        this.columnas = columnas;
        this.casillas_ancho = casillas_ancho;
        this.casilla_alto = casilla_alto;
    }

    public Canvas ActualizarTablero(ArrayList<Object> estado_juego) {
        Canvas canvas = new Canvas(ancho_canvas, alto_canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int x = 0; x <= columnas; x++) {
            for (int y = 0; y <= filas; y++) {
                if ((x + y) % 2 == 0) {
                    gc.setFill(Color.WHITE);
                } else {
                    gc.setFill(Color.GRAY);
                }
                gc.fillRect(x * casillas_ancho, y * casilla_alto, casillas_ancho, casilla_alto);
            }
        }
        return canvas;
    }
}
