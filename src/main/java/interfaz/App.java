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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.stage.StageStyle;
import logica.Sistema;

import java.util.ArrayList;


/**
 * JavaFX App
 */
public class App extends Application {
    private boolean juego_terminado = false;
    private final static String NOMBRE_JUEGO = "Robots";
    private final static String BOTON_ALEATORIO = "Teleport Randomly";
    private final static String BOTON_SEGURO = "Teleport Safely";
    private final static String BOTON_ESPERAR = "Wait";
    private final static String TEXTO_FIN = "GAME OVER";
    private final static String BOTON_REINICIAR = "Reiniciar";
    private final static String BOTON_SALIR = "Salir";
    private static int TAMANIO_MENUES = 150;
    private int ALTO_VENTANA = 900;
    private static int FILAS = 5;
    private static int COLUMNAS = 5;
    private int CASILLA_TAMANIO = (ALTO_VENTANA - TAMANIO_MENUES * 2) / FILAS;
    private int ANCHO_VENTANA = CASILLA_TAMANIO * COLUMNAS;
    private int ANCHO_CANVAS = ANCHO_VENTANA;
    private int ALTO_CANVAS = CASILLA_TAMANIO * FILAS;
    /*
    private static int TAMANIO_MENUES = 150;
    private int ALTO_VENTANA = 900;
    private static int FILAS = 40;
    private static int COLUMNAS = 50;
    private int CASILLA_TAMANIO = (ALTO_VENTANA - TAMANIO_MENUES * 2) / FILAS;
    private int CASILLA_ANCHO = CASILLA_TAMANIO;
    private int CASILLA_ALTO = CASILLA_TAMANIO;
    private int ANCHO_CANVAS = CASILLA_ANCHO * COLUMNAS;
    private int ALTO_CANVAS = CASILLA_ALTO * FILAS;
    private int ANCHO_VENTANA = ANCHO_CANVAS;
    */


    private Sistema sistema = new Sistema(0,1);
    private Stage stage;
    private boolean tp_seguro_activado = false;
    private final Evento Evento = new Evento();

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Tablero tablero = new Tablero(FILAS, COLUMNAS, CASILLA_TAMANIO);

        int nivel = sistema.getNivel();
        int score = sistema.getScore();
        int tps_seguros = sistema.getTpsSeguros();

        VBox root = new VBox();
        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA, Color.BLACK);

        HBox casilla_superior = new HBox();
        casilla_superior.setPrefHeight(TAMANIO_MENUES);
        casilla_superior.setStyle("-fx-background-color: PERU; -fx-border-color: black; -fx-border-width: 2;");
        casilla_superior.setAlignment(Pos.CENTER);
        VBox titulo_stats = inicializar_textos(nivel, score);

        Canvas canvas = tablero.ActualizarTablero(sistema.estadoJuego(), ANCHO_CANVAS, ALTO_CANVAS, CASILLA_TAMANIO);

        HBox casilla_inferior = new HBox(5);
        casilla_inferior.setPrefHeight(TAMANIO_MENUES);
        casilla_inferior.setStyle("-fx-background-color: PERU; -fx-border-color: black; -fx-border-width: 1;");
        casilla_inferior.setAlignment(Pos.CENTER);
        Boton[] botones = inicializar_botones(tps_seguros);
        Boton boton_tp_aleatorio = botones[0]; Boton boton_tp = botones[1]; Boton boton_no_moverser = botones[2];

        boton_tp_aleatorio.setOnAction( _ -> {
            juego_terminado = Evento.botonTpAleatorioActivado(sistema);
            start(stage);
        });
        boton_tp.setOnAction( _ -> {
            Evento.botonTpActivado(sistema);
        });
        boton_no_moverser.setOnAction( _ -> {
            juego_terminado = Evento.botonNoMoverseActivado(sistema);
            start(stage);
        });
        scene.setOnKeyPressed(keyEvent -> {
            juego_terminado = Evento.teclaAccionada(sistema,keyEvent.getCode().toString());
            start(stage);
        });
        scene.setOnMouseClicked((MouseEvent mouseEvent) -> {
            int[] coordenadas = new int[]{((int) mouseEvent.getY() - TAMANIO_MENUES) / CASILLA_TAMANIO, (int) mouseEvent.getX() / CASILLA_TAMANIO};
            juego_terminado = Evento.mouseAccionado(sistema,coordenadas);
            start(stage);
        });

        casilla_superior.getChildren().add(titulo_stats);
        casilla_inferior.getChildren().addAll(boton_tp_aleatorio, boton_tp, boton_no_moverser);
        root.getChildren().addAll(casilla_superior, canvas, casilla_inferior);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(NOMBRE_JUEGO);
        stage.show();

        if (sistema.juegoGanado() && sistema.jugadorEstaVivo()){
            inicializarSiguienteNivel();
            start(stage);
        }
        if (juego_terminado) {
            finalizar_juego(sistema.getNivel(), sistema.getScore());
        }
    }

    private void inicializarSiguienteNivel() {
        sistema = new Sistema(sistema.getScore(),sistema.getNivel() + 1);
    }


    private static VBox inicializar_textos(int nivel, int score) {
        Text titulo = new Text(NOMBRE_JUEGO);
        Text stats = new Text(String.format("Level: %d | Score: %d", nivel, score));
        stats.setFill(Color.WHEAT);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        stats.setFont(Font.font("Arial", FontWeight.THIN, 25));

        VBox titulo_stats = new VBox();
        titulo_stats.setAlignment(Pos.TOP_CENTER);
        titulo_stats.setPadding(new Insets(25, 0, 0, 0));
        titulo_stats.setSpacing(10);
        titulo_stats.getChildren().addAll(titulo, stats);

        return titulo_stats;
    }

    private Boton[] inicializar_botones(int tps_seguros) {
        Boton tp_aleatorio = new Boton(STR."\{BOTON_ALEATORIO} (O)",ANCHO_VENTANA / 3,140);
        Boton tp_seguro = new Boton(String.format("%s: %d (P)", BOTON_SEGURO, tps_seguros),ANCHO_VENTANA / 3,140);
        Boton no_moverse = new Boton(STR. "\{BOTON_ESPERAR} (S)",ANCHO_VENTANA / 3,140);
        return new Boton[]{tp_aleatorio, tp_seguro, no_moverse};
    }

    private VBox textos_fin(Text mensaje_top, Text mensaje_bot) {
        mensaje_top.setFill(Color.BLACK);
        mensaje_top.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        mensaje_bot.setFont(Font.font("Arial", FontWeight.THIN, 15));

        VBox mensajes = new VBox();
        mensajes.setAlignment(Pos.TOP_CENTER);
        mensajes.setPadding(new Insets(25, 0, 0, 0));
        mensajes.setSpacing(10);
        mensajes.getChildren().addAll(mensaje_top, mensaje_bot);

        return mensajes;
    }

    private void finalizar_juego(int nivel, int score) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UNDECORATED);

        VBox popupRoot = new VBox();
        popupRoot.setAlignment(Pos.CENTER);
        HBox popupBotones = new HBox();
        popupBotones.setAlignment(Pos.BOTTOM_CENTER);
        popupBotones.setSpacing(10);

        Text game_over = new Text(TEXTO_FIN);
        Text mensaje = new Text(String.format("Level: %d | Score: %d", nivel, score));

        Boton boton_1 = new Boton(BOTON_REINICIAR, 100, 50);
        Boton boton_2 = new Boton(BOTON_SALIR, 100, 50);

        VBox textos = textos_fin(game_over, mensaje);
        popupRoot.getChildren().addAll(popupBotones, textos);

        boton_1.setOnAction(_ ->{
            sistema =new Sistema(0,1);
            juego_terminado = false;
            start(stage);
            popup.close();
        });
        boton_2.setOnAction(event -> System.exit(0));

        popupRoot.setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #a6a6a6);" +
                "-fx-border-width: 2px; -fx-border-color: black;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");

        Scene popupScene = new Scene(popupRoot, 400, 200);
        popup.setScene(popupScene);

        popupBotones.getChildren().addAll(boton_1, boton_2);
        popup.setOnCloseRequest(event -> event.consume());
        popup.showAndWait();
    }
    public static void main(String[] args) {
        Application.launch();
    }
}