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
    private static int ALTO_VENTANA = 900;
    private static int FILAS = 15;
    private static int COLUMNAS = 15;
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
    private Sistema sistema = new Sistema(0,1);
    private Stage stage;
    private boolean tp_seguro_activado = false;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Tablero tablero = new Tablero(ANCHO_VENTANA, ALTO_VENTANA, ANCHO_CANVAS, ALTO_CANVAS, FILAS, COLUMNAS, CASILLA_ANCHO, CASILLA_ALTO);

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

        StackPane lienzo_canvas = new StackPane();
        Canvas canvas = tablero.ActualizarTablero(sistema.estadoJuego(),TAMANIO_MENUES);
        lienzo_canvas.getChildren().add(canvas);
        lienzo_canvas.setAlignment(Pos.CENTER);

        HBox casilla_inferior = new HBox(5);
        casilla_inferior.setPrefHeight(TAMANIO_MENUES);
        casilla_inferior.setStyle("-fx-background-color: PERU; -fx-border-color: black; -fx-border-width: 1;");
        casilla_inferior.setAlignment(Pos.CENTER);
        Boton[] botones = inicializar_botones(tps_seguros);
        Boton boton_tp_aleatorio = botones[0]; Boton boton_tp = botones[1]; Boton boton_no_moverser = botones[2];

        boton_tp.setOnAction(boton_tp.ActivarEvento());
        boton_tp_aleatorio.setOnAction((boton_tp_aleatorio.ActivarEvento()));
        boton_no_moverser.setOnAction((boton_no_moverser.ActivarEvento()));

        casilla_superior.getChildren().add(titulo_stats);
        casilla_inferior.getChildren().addAll(boton_tp_aleatorio, boton_tp, boton_no_moverser);
        root.getChildren().addAll(casilla_superior, lienzo_canvas, casilla_inferior);

        scene.setOnKeyPressed(keyEvent -> {
            String codigo_marcado = keyEvent.getCharacter();
        });

        scene.setOnMouseClicked((MouseEvent mouseEvent) -> {
            int[] coordenadas = new int[]{((int) mouseEvent.getY() - TAMANIO_MENUES) / CASILLA_ALTO, (int) mouseEvent.getX() / CASILLA_ANCHO};
            if (tp_seguro_activado){
                juego_terminado = sistema.JugarTpSeguro(coordenadas);
                tp_seguro_activado = false;
            }else{
                juego_terminado = sistema.jugarTurno(coordenadas);
            }
            start(stage);
        });

        stage.setScene(scene);
        stage.setResizable(false);
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

    public static void main(String[] args) {
        Application.launch();
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
        EventHandler<ActionEvent> evento_tp_aleatorio = event -> {
            juego_terminado = sistema.jugarTpAleatorio();
            start(stage);
        };
        EventHandler<ActionEvent> evento_tp_seguro = event -> {
            if (sistema.getTpsSeguros() > 0) {
                tp_seguro_activado = true;
            }
        };
        EventHandler<ActionEvent> evento_no_moverse = event -> {
            juego_terminado = sistema.jugarTurno(new int[]{-1,-1});
            start(stage);
        };
        Boton tp_aleatorio = new Boton(BOTON_ALEATORIO, evento_tp_aleatorio,ANCHO_VENTANA / 3,140);
        Boton tp_seguro = new Boton(String.format("%s: %d", BOTON_SEGURO, tps_seguros), evento_tp_seguro,ANCHO_VENTANA / 3,140);
        Boton no_moverse = new Boton(BOTON_ESPERAR, evento_no_moverse,ANCHO_VENTANA / 3,140);
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

        EventHandler<ActionEvent> evento_reiniciar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sistema = new Sistema(0,1);
                juego_terminado = false;
                start(stage);
                popup.close();
            }
        };
        EventHandler<ActionEvent> evento_salir = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        };

        Text game_over = new Text(TEXTO_FIN);
        Text mensaje = new Text(String.format("Level: %d | Score: %d", nivel, score));

        Boton boton_1 = new Boton(BOTON_REINICIAR, evento_reiniciar, 100, 50);
        Boton boton_2 = new Boton(BOTON_SALIR, evento_salir, 100, 50);

        VBox textos = textos_fin(game_over, mensaje);
        popupRoot.getChildren().addAll(popupBotones, textos);

        boton_1.setOnAction(boton_1.ActivarEvento());
        boton_2.setOnAction(boton_2.ActivarEvento());

        popupRoot.setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #a6a6a6);" +
                "-fx-border-width: 2px; -fx-border-color: black;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");

        Scene popupScene = new Scene(popupRoot, 400, 200);
        popup.setScene(popupScene);

        popupBotones.getChildren().addAll(boton_1, boton_2);
        popup.setOnCloseRequest(event -> event.consume());
        popup.showAndWait();
    }
}