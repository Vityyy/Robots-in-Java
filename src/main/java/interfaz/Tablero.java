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
    private int filas;
    private int columnas;
    private int tamanio_casilla;

    public Tablero(int filas, int columnas, int tamanio_casilla) {
        this.filas = filas;
        this.columnas = columnas;
        this.tamanio_casilla = tamanio_casilla;
    }

    public Canvas ActualizarTablero(ArrayList<Object> estado_juego, int ancho_canvas, int alto_canvas, int tamanio_casilla) {
        Canvas canvas = new Canvas(ancho_canvas, alto_canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        var posicion_jugador = (int[]) estado_juego.get(0);
        var posiciones_robots = (HashMap<Enemigo,int[]>) estado_juego.get(1);

        for (int x = 0; x < filas; x++) {
            for (int y = 0; y < columnas; y++) {
                if ((x + y) % 2 == 0) {
                    gc.setFill(Color.BURLYWOOD);
                } else {
                    gc.setFill(Color.SADDLEBROWN);
                }
                gc.fillRect(y * tamanio_casilla, x * tamanio_casilla, tamanio_casilla, tamanio_casilla);
            }
        }
        Image sprite_jugador = new Image("file:src/main/resources/rayman.png");
        gc.drawImage(sprite_jugador, posicion_jugador[1] * tamanio_casilla, posicion_jugador[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);

        Image sprite_simple = new Image("file:src/main/resources/rotom.png");
        Image sprite_complejo = new Image("file:src/main/resources/sans.png");
        Image sprite_colision = new Image("file:src/main/resources/fuego.png");

        for (Enemigo enemigo : posiciones_robots.keySet()){
            var coordenadas_robot = posiciones_robots.get(enemigo);
            if (enemigo.getFuncional() && (enemigo.getClass().getName().equals("logica.RobotSimple"))){
                gc.drawImage(sprite_simple, coordenadas_robot[1] * tamanio_casilla, coordenadas_robot[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);

            }else if (enemigo.getFuncional() && (enemigo.getClass().getName().equals("logica.RobotComplejo"))){
                 gc.drawImage(sprite_complejo, coordenadas_robot[1] * tamanio_casilla, coordenadas_robot[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);

            }else if (!enemigo.getFuncional())
                gc.drawImage(sprite_colision, coordenadas_robot[1] * tamanio_casilla, coordenadas_robot[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);
            }
        return canvas;
    }
}