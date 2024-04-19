package interfaz;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import logica.Sistema;

import java.util.ArrayList;


/**
 * JavaFX App
 */
public class App extends Application {

    private int ANCHO_VENTANA = 1300;
    private int ALTO_VENTANA = 900;
    private int ANCHO_CANVAS = 1300;
    private int ALTO_CANVAS = 600;
    private int FILAS = 40;
    private int COLUMNAS = 40;
    private int CASILLA_ANCHO = ANCHO_CANVAS / COLUMNAS;
    private int CASILLA_ALTO = ALTO_VENTANA / FILAS;

    private Sistema sistema;


    @Override
    public void start(Stage stage) {

        this.sistema = new Sistema();

        VBox titulo_stats = inicializar_textos();;

        Boton[] botones = inicializar_botones();
        Boton boton_tp_aleatorio = botones[0]; Boton boton_tp = botones[1]; Boton boton_no_moverser = botones[2];

        StackPane root = new StackPane();
        StackPane pane = new StackPane();
        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA, Color.BLACK);
        Canvas casilla_superior = new Canvas(ANCHO_CANVAS, 150);
        Canvas casilla_inferior = new Canvas(ANCHO_CANVAS, 150);
        Tablero tablero = new Tablero(ANCHO_VENTANA, ALTO_VENTANA, ANCHO_CANVAS, ALTO_CANVAS, FILAS, COLUMNAS, CASILLA_ANCHO, CASILLA_ALTO);

        Canvas canvas = tablero.ActualizarTablero(sistema.estadoJuego());
        boton_tp.setOnAction(boton_tp.ActivarEvento());
        boton_tp_aleatorio.setOnAction((boton_tp_aleatorio.ActivarEvento()));
        boton_no_moverser.setOnAction((boton_no_moverser.ActivarEvento()));

        scene.setOnMouseClicked((MouseEvent mouseEvent) -> {
            int[] coordenadas = new int[]{(int) mouseEvent.getX() / CASILLA_ANCHO, (int) mouseEvent.getY() / CASILLA_ALTO};
            sistema.jugarTurno(coordenadas);
            tablero.ActualizarTablero(sistema.estadoJuego());
        });


        posicionar_interfaz(canvas, casilla_superior, casilla_inferior, boton_tp, boton_tp_aleatorio, boton_no_moverser, titulo_stats);
        GraphicsContext barra_superior = casilla_superior.getGraphicsContext2D();
        GraphicsContext barra_inferior = casilla_inferior.getGraphicsContext2D();
        barra_superior.setFill(Color.SLATEGRAY);
        barra_inferior.setFill(Color.SLATEGRAY);
        barra_superior.fillRoundRect(0, 0, ANCHO_CANVAS, 150, 40, 40);
        barra_inferior.fillRoundRect(0, 50, ANCHO_CANVAS, 150, 40, 40);

        root.getChildren().add(pane);
        pane.getChildren().addAll(canvas, casilla_superior, casilla_inferior, boton_tp_aleatorio, boton_tp, boton_no_moverser, titulo_stats);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch();
    }


    private static void posicionar_interfaz(Canvas canvas, Canvas casilla_superior, Canvas casilla_inferior, Button tp_seguro, Button tp_aleatorio, Button no_moverse, VBox titulo_stats) {
        StackPane.setAlignment(canvas, Pos.CENTER);
        StackPane.setAlignment(casilla_superior, Pos.TOP_CENTER);
        StackPane.setAlignment(casilla_inferior, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(tp_seguro, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(tp_aleatorio, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(no_moverse, Pos.BOTTOM_RIGHT);

        titulo_stats.setAlignment(Pos.TOP_CENTER);
        titulo_stats.setPadding(new Insets(25, 0, 0, 0));
        titulo_stats.setSpacing(10);
    }

    private static VBox inicializar_textos() {
        Text titulo = new Text("Robots");
        Text stats = new Text("Level: pene vs Score: vagina");
        stats.setFill(Color.LIGHTGRAY);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        stats.setFont(Font.font("Arial", FontWeight.THIN, 25));

        VBox titulo_stats = new VBox();
        titulo_stats.getChildren().addAll(titulo, stats);

        return titulo_stats;
    }

    private static Boton[] inicializar_botones() {
        EventHandler<ActionEvent> evento_tp_aleatorio = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("TOCASTE BOTON ALEATORIO");
            }
        };
        EventHandler<ActionEvent> evento_tp_seguro = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        };
        EventHandler<ActionEvent> evento_no_moverse = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        };
        Boton tp_aleatorio = new Boton("TP ALEATORIO",evento_tp_aleatorio,430,150);
        Boton tp_seguro = new Boton("TP SEGURO", evento_tp_seguro,430,150);
        Boton no_moverse = new Boton("NO MOVERSE",evento_no_moverse,430,150);
        return new Boton[]{tp_aleatorio, tp_seguro, no_moverse};
    }
}