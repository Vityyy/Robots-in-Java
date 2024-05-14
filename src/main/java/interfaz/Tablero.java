package interfaz;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logica.Robot;

import java.util.ArrayList;
import java.util.HashMap;

public class Tablero {
    private static final String DIRECCION_PJ = "file:src/main/resources/rayman.png";
    private static final String DIRECCION_SIMPLE = "file:src/main/resources/rotom.png";
    private static final String DIRECCION_COMPLEJO = "file:src/main/resources/sans.png";
    private static final String DIRECCION_COLISION = "file:src/main/resources/fuego.png";
    private final int ancho_canvas;
    private final int alto_canvas;
    private final int filas;
    private final int columnas;
    private final int tamanio_casilla;

    public Tablero(int filas, int columnas, int tamanio_casilla, int ancho_canvas, int alto_canvas) {
        this.filas = filas;
        this.columnas = columnas;
        this.tamanio_casilla = tamanio_casilla;
        this.ancho_canvas = ancho_canvas;
        this.alto_canvas = alto_canvas;
    }
    public Canvas ActualizarTablero(ArrayList<Object> estado_juego) {
        Canvas canvas = new Canvas(ancho_canvas, alto_canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        var posicion_jugador = (int[]) estado_juego.get(0);
        var posiciones_robots = (HashMap<Robot,int[]>) estado_juego.get(1);

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
        Image sprite_jugador = new Image(DIRECCION_PJ);
        gc.drawImage(sprite_jugador, posicion_jugador[1] * tamanio_casilla, posicion_jugador[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);

        Image sprite_simple = new Image(DIRECCION_SIMPLE);
        Image sprite_complejo = new Image(DIRECCION_COMPLEJO);
        Image sprite_colision = new Image(DIRECCION_COLISION);

        for (Robot robot : posiciones_robots.keySet()){
            var coordenadas_robot = posiciones_robots.get(robot);
            if (robot.getFuncional() && (robot.getClass().getName().equals("logica.RobotComplejo"))){
                gc.drawImage(sprite_complejo, coordenadas_robot[1] * tamanio_casilla, coordenadas_robot[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);

            }else if (robot.getFuncional() && (robot.getClass().getName().equals("logica.Robot"))){
                gc.drawImage(sprite_simple, coordenadas_robot[1] * tamanio_casilla, coordenadas_robot[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);

            }else if (!robot.getFuncional())
                gc.drawImage(sprite_colision, coordenadas_robot[1] * tamanio_casilla, coordenadas_robot[0] * tamanio_casilla, tamanio_casilla, tamanio_casilla);
            }
        return canvas;
    }
}