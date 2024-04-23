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
    private static int TAMANIO_MENUES = 150;
    private static int ALTO_VENTANA = 900;
    private static int FILAS = 40;
    private static int COLUMNAS = 40;
    private static int CASILLA_TAMANIO = (ALTO_VENTANA - TAMANIO_MENUES * 2) / FILAS;
    private static int CASILLA_ANCHO = CASILLA_TAMANIO;
    private static int CASILLA_ALTO = CASILLA_TAMANIO;
    private static int ANCHO_CANVAS = CASILLA_ANCHO * COLUMNAS;
    private static int ALTO_CANVAS = CASILLA_ALTO * FILAS;
    private static int ANCHO_VENTANA = ANCHO_CANVAS;
    /*private int ANCHO_VENTANA = 1300;
    private int ALTO_VENTANA = 900;
    private int FILAS = 40;
    private int COLUMNAS = 40;
    private int CASILLA_AUX = Math.min(ANCHO_VENTANA / COLUMNAS, ALTO_VENTANA / FILAS);;
    private int ANCHO_CANVAS = CASILLA_AUX * COLUMNAS;
    //1300;
    private int ALTO_CANVAS = (CASILLA_AUX * FILAS) - 300;
    //600
    private int CASILLA_TAMANIO = Math.min(ANCHO_CANVAS / COLUMNAS, ALTO_CANVAS / FILAS);
    private int CASILLA_ANCHO = CASILLA_TAMANIO;
    //ANCHO_CANVAS / COLUMNAS;
    private int CASILLA_ALTO = CASILLA_TAMANIO;
    //ALTO_VENTANA / FILAS;
     */

    private Sistema sistema = new Sistema();

    @Override
    public void start(Stage stage) {

        Tablero tablero = new Tablero(ANCHO_VENTANA, ALTO_VENTANA, ANCHO_CANVAS, ALTO_CANVAS, FILAS, COLUMNAS, CASILLA_ANCHO, CASILLA_ALTO);

        VBox root = new VBox();
        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA, Color.BLACK);

        HBox casilla_superior = new HBox();
        casilla_superior.setPrefHeight(TAMANIO_MENUES);
        casilla_superior.setStyle("-fx-background-color: SLATEGRAY;");
        casilla_superior.setAlignment(Pos.CENTER);
        VBox titulo_stats = inicializar_textos();

        Canvas canvas = tablero.ActualizarTablero(sistema.estadoJuego(),TAMANIO_MENUES);

        HBox casilla_inferior = new HBox(5);
        casilla_inferior.setPrefHeight(TAMANIO_MENUES);
        casilla_inferior.setStyle("-fx-background-color: SLATEGRAY;");
        casilla_inferior.setAlignment(Pos.CENTER);
        Boton[] botones = inicializar_botones();
        Boton boton_tp_aleatorio = botones[0]; Boton boton_tp = botones[1]; Boton boton_no_moverser = botones[2];

        boton_tp.setOnAction(boton_tp.ActivarEvento());
        boton_tp_aleatorio.setOnAction((boton_tp_aleatorio.ActivarEvento()));
        boton_no_moverser.setOnAction((boton_no_moverser.ActivarEvento()));

        casilla_superior.getChildren().add(titulo_stats);
        casilla_inferior.getChildren().addAll(boton_tp, boton_tp_aleatorio, boton_no_moverser);
        root.getChildren().addAll(casilla_superior, canvas, casilla_inferior);

        scene.setOnMouseClicked((MouseEvent mouseEvent) -> {
            int[] coordenadas = new int[]{((int) mouseEvent.getY() - TAMANIO_MENUES + CASILLA_TAMANIO) / CASILLA_ALTO, (int) mouseEvent.getX() / CASILLA_ANCHO};
            sistema.jugarTurno(coordenadas);
            System.out.println(coordenadas[0]);
            System.out.println(coordenadas[1]);
            start(stage);
        });

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }

    private static VBox inicializar_textos() {
        Text titulo = new Text("Robots");
        Text stats = new Text("Level: pene vs Score: vagina");
        stats.setFill(Color.LIGHTGRAY);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        stats.setFont(Font.font("Arial", FontWeight.THIN, 25));

        VBox titulo_stats = new VBox();
        titulo_stats.setAlignment(Pos.TOP_CENTER);
        titulo_stats.setPadding(new Insets(25, 0, 0, 0));
        titulo_stats.setSpacing(10);
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
        Boton tp_aleatorio = new Boton("TP ALEATORIO",evento_tp_aleatorio,ANCHO_VENTANA / 3,140); //430 , 150
        Boton tp_seguro = new Boton("TP SEGURO", evento_tp_seguro,ANCHO_VENTANA / 3,140);
        Boton no_moverse = new Boton("NO MOVERSE",evento_no_moverse,ANCHO_VENTANA / 3,140);
        return new Boton[]{tp_aleatorio, tp_seguro, no_moverse};
    }
}