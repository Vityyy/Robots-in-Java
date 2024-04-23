package interfaz;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logica.Enemigo;

import java.util.ArrayList;
import java.util.HashMap;

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

    public Canvas ActualizarTablero(ArrayList<Object> estado_juego, int Tamanios_Menues) {
        Canvas canvas = new Canvas(ancho_canvas, alto_canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        var posicion_jugador = (int[]) estado_juego.get(0);
        var posiciones_robots = (HashMap<Enemigo,int[]>) estado_juego.get(1);

        for (int x = 0; x <= filas; x++) {
            for (int y = 0; y <= columnas; y++) {
                if ((x + y) % 2 == 0) {
                    gc.setFill(Color.WHITE);
                } else {
                    gc.setFill(Color.GRAY);
                }
                gc.fillRect(x * casillas_ancho, y * casilla_alto, casillas_ancho, casilla_alto);
            }
        }
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("X", posicion_jugador[1] * casilla_alto, posicion_jugador[0] * casillas_ancho);

        for (Enemigo enemigo : posiciones_robots.keySet()){
            var coordenadas_robot = posiciones_robots.get(enemigo);
            if (enemigo.getFuncional() && (enemigo.getClass().getName().equals("logica.RobotSimple"))){
                gc.fillText("S",coordenadas_robot[1] * casillas_ancho,coordenadas_robot[0] * casilla_alto);
            }else if (enemigo.getFuncional() && (enemigo.getClass().getName().equals("logica.RobotComplejo"))){
                gc.fillText("C",coordenadas_robot[1] * casillas_ancho,coordenadas_robot[0] * casilla_alto);
            }else if (!enemigo.getFuncional())
                gc.fillText("M",coordenadas_robot[1] * casillas_ancho,coordenadas_robot[0] * casilla_alto);
            }
        return canvas;
    }
}
