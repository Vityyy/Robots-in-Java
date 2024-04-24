package interfaz;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private int casilla_ancho;
    private int casilla_alto;

    public Tablero(int ancho_ventana, int alto_ventana, int ancho_canvas, int alto_canvas, int filas, int columnas, int casilla_ancho, int casilla_alto) {
        this.ancho_ventana = ancho_ventana;
        this.alto_ventana = alto_ventana;
        this.ancho_canvas = ancho_canvas;
        this.alto_canvas = alto_canvas;
        this.filas = filas;
        this.columnas = columnas;
        this.casilla_ancho = casilla_ancho;
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
                    gc.setFill(Color.BURLYWOOD);
                } else {
                    gc.setFill(Color.SADDLEBROWN);
                }
                gc.fillRect(x * casilla_ancho, y * casilla_alto, casilla_ancho, casilla_alto);
            }
        }
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", 20));
        Image sprite_jugador = new Image("file:src/main/resources/rayman.png");
        gc.drawImage(sprite_jugador, posicion_jugador[1] * casilla_alto, posicion_jugador[0] * casilla_ancho, casilla_ancho, casilla_alto);

        Image sprite_simple = new Image("file:src/main/resources/rotom.png");
        Image sprite_complejo = new Image("file:src/main/resources/sans.png");
        Image sprite_colision = new Image("file:src/main/resources/fuego.png");

        for (Enemigo enemigo : posiciones_robots.keySet()){
            var coordenadas_robot = posiciones_robots.get(enemigo);
            if (enemigo.getFuncional() && (enemigo.getClass().getName().equals("logica.RobotSimple"))){
                gc.drawImage(sprite_simple, coordenadas_robot[1] * casilla_alto, coordenadas_robot[0] * casilla_ancho, casilla_ancho, casilla_alto);

            }else if (enemigo.getFuncional() && (enemigo.getClass().getName().equals("logica.RobotComplejo"))){
                 gc.drawImage(sprite_complejo, coordenadas_robot[1] * casilla_alto, coordenadas_robot[0] * casilla_ancho, casilla_ancho, casilla_alto);

            }else if (!enemigo.getFuncional())
                gc.drawImage(sprite_colision, coordenadas_robot[1] * casilla_alto, coordenadas_robot[0] * casilla_ancho, casilla_ancho, casilla_alto);
            }
        return canvas;
    }
}
