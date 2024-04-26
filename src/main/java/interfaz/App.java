package interfaz;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.stage.StageStyle;
import logica.Sistema;

/**
 * JavaFX App
 */
public class App extends Application {
    private boolean juego_terminado = false;
    private final static String NOMBRE_JUEGO = "Robots";
    private final static String FONT = "Arial";
    private final static String DISENIO_SUPERIOR = "-fx-background-color: PERU; -fx-border-color: black; -fx-border-width: 2;";
    private final static String DISENIO_INFERIOR = "-fx-background-color: PERU; -fx-border-color: black; -fx-border-width: 1;";
    private final static String DISENIO_POPUP = "-fx-background-color: linear-gradient(to bottom, #f2f2f2, #a6a6a6); -fx-border-width: 2px;" +
            "-fx-border-color: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);";
    private final static String BOTON_ALEATORIO = "Teleport Randomly";
    private final static String BOTON_SEGURO = "Teleport Safely";
    private final static String BOTON_ESPERAR = "Wait";
    private final static String BOTON_TAMANIO = "Dimensión";
    private final static String BOTON_ACEPTAR = "Accept";
    private final static String TEXTO_FIN = "GAME OVER";
    private final static String BOTON_REINICIAR = "Reiniciar";
    private final static String BOTON_SALIR = "Salir";
    private final static int TAMANIO_MENUES = 150;
    private final static int ALTO_VENTANA = 900;
    private static int FILAS = 20;
    private static int COLUMNAS = 40;
    private int CASILLA_TAMANIO = (ALTO_VENTANA - TAMANIO_MENUES * 2) / FILAS;
    private int ANCHO_VENTANA = CASILLA_TAMANIO * COLUMNAS;
    private int ANCHO_CANVAS = ANCHO_VENTANA;
    private int ALTO_CANVAS = CASILLA_TAMANIO * FILAS;
    private Sistema sistema = new Sistema(0,1,FILAS,COLUMNAS);
    private Stage stage;
    private final Evento Evento = new Evento();

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Tablero tablero = new Tablero(FILAS, COLUMNAS, CASILLA_TAMANIO, ANCHO_CANVAS, ALTO_CANVAS);

        int nivel = sistema.getNivel();
        int score = sistema.getScore();
        int tps_seguros = sistema.getTpsSeguros();

        VBox root = new VBox();
        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA, Color.BLACK);

        StackPane casilla_superior = new StackPane();
        HBox tamanioBox = new HBox();
        casilla_superior.setPrefHeight(TAMANIO_MENUES); casilla_superior.setStyle(DISENIO_SUPERIOR); casilla_superior.setAlignment(Pos.CENTER);
        VBox titulo_stats = inicializar_textos(nivel, score);

        Canvas canvas = tablero.ActualizarTablero(sistema.estadoJuego());

        HBox casilla_inferior = new HBox(5);
        casilla_inferior.setPrefHeight(TAMANIO_MENUES); casilla_inferior.setStyle(DISENIO_INFERIOR); casilla_inferior.setAlignment(Pos.CENTER);
        Boton[] botones = inicializar_botones(tps_seguros);
        Boton boton_tp_aleatorio = botones[0]; Boton boton_tp = botones[1]; Boton boton_no_moverser = botones[2]; Boton boton_tamanio = botones[3];

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
        boton_tamanio.setOnAction( _ -> {
            popup_tamanio();
            start(stage);
        });
        scene.setOnKeyPressed(keyEvent -> {
            juego_terminado = Evento.teclaAccionada(sistema,keyEvent.getCode().toString());
            start(stage);
        });
        scene.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getY() >= TAMANIO_MENUES && mouseEvent.getY() <= ALTO_CANVAS + TAMANIO_MENUES) {
                int[] coordenadas = new int[]{((int) mouseEvent.getY() - TAMANIO_MENUES) / CASILLA_TAMANIO, (int) mouseEvent.getX() / CASILLA_TAMANIO};
                juego_terminado = Evento.mouseAccionado(sistema, coordenadas);
                start(stage);
            }
        });


        tamanioBox.getChildren().add(boton_tamanio);
        tamanioBox.setAlignment(Pos.BOTTOM_RIGHT);

        casilla_superior.getChildren().addAll(titulo_stats, tamanioBox);
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
        sistema = new Sistema(sistema.getScore(),sistema.getNivel() + 1,FILAS,COLUMNAS);
    }

    private static VBox inicializar_textos(int nivel, int score) {
        Text titulo = new Text(NOMBRE_JUEGO);
        Text stats = new Text(String.format("Level: %d | Score: %d", nivel, score));
        stats.setFill(Color.WHEAT);
        titulo.setFont(Font.font(FONT, FontWeight.BOLD, 25));
        stats.setFont(Font.font(FONT, FontWeight.THIN, 25));

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
        Boton tamanio = new Boton(STR."\{BOTON_TAMANIO} (O)",ANCHO_VENTANA / 8,40);
        return new Boton[]{tp_aleatorio, tp_seguro, no_moverse, tamanio};
    }

    private VBox textos_fin(Text mensaje_top, Text mensaje_bot) {
        mensaje_top.setFill(Color.BLACK);
        mensaje_top.setFont(Font.font(FONT, FontWeight.BOLD, 25));
        mensaje_bot.setFont(Font.font(FONT, FontWeight.THIN, 15));

        VBox mensajes = new VBox();
        mensajes.setAlignment(Pos.TOP_CENTER);
        mensajes.setPadding(new Insets(25, 0, 0, 0));
        mensajes.setSpacing(10);
        mensajes.getChildren().addAll(mensaje_top, mensaje_bot);

        return mensajes;
    }

    private Object[] creacion_popup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UNDECORATED);

        VBox popupRoot = new VBox();
        popupRoot.setAlignment(Pos.CENTER);

        HBox popupBotones = new HBox();
        popupBotones.setAlignment(Pos.BOTTOM_CENTER);
        popupBotones.setSpacing(10);

        return new Object[]{popup, popupRoot, popupBotones};
    }

    private void popup_tamanio() {
        Object[] popups = creacion_popup();
        Stage popup = (Stage) popups[0];
        VBox popupRoot = (VBox) popups[1];
        Text mensaje_superior = new Text("Ingrese el tamaño nuevo de grilla deseado");

        TextField filas_input = new TextField("Ingrese nº de filas");
        TextField columnas_input = new TextField("Ingrese nº de columnas");
        filas_input.setPrefWidth(300); columnas_input.setPrefWidth(300);
        filas_input.setPrefHeight(100); columnas_input.setPrefHeight(100);
        filas_input.setAlignment(Pos.CENTER); columnas_input.setAlignment(Pos.CENTER);

        Boton boton = new Boton(BOTON_ACEPTAR, 250, 50);

        boton.setOnAction( _ -> {
            String nuevas_filas = filas_input.getText();
            String nuevas_columnas = columnas_input.getText();
            sistema = Evento.RedimensionarJuego(sistema, nuevas_filas, nuevas_columnas);
            FILAS = sistema.getDimension()[0]; COLUMNAS = sistema.getDimension()[1];
            CASILLA_TAMANIO = (ALTO_VENTANA - TAMANIO_MENUES * 2) / FILAS;
            ANCHO_VENTANA = CASILLA_TAMANIO * COLUMNAS;
            ANCHO_CANVAS = ANCHO_VENTANA;
            ALTO_CANVAS = CASILLA_TAMANIO * FILAS;
            start(stage);
            popup.close();
        });

        Scene popupScene = new Scene(popupRoot, 400, 300);

        popupRoot.getChildren().addAll(mensaje_superior, filas_input, columnas_input, boton);
        popupRoot.setStyle(DISENIO_POPUP);
        popup.setScene(popupScene);
        popup.setOnCloseRequest(Event::consume);
        popup.showAndWait();
    }
    private void finalizar_juego(int nivel, int score) {
        Object[] popups = creacion_popup();
        Stage popup = (Stage) popups[0];
        VBox popupRoot = (VBox) popups[1];
        HBox popupBotones = (HBox) popups[2];

        Text game_over = new Text(TEXTO_FIN);
        Text mensaje = new Text(String.format("Level: %d | Score: %d", nivel, score));

        Boton boton_1 = new Boton(BOTON_REINICIAR, 100, 50);
        Boton boton_2 = new Boton(BOTON_SALIR, 100, 50);

        VBox textos = textos_fin(game_over, mensaje);
        popupRoot.getChildren().addAll(popupBotones, textos);

        boton_1.setOnAction(_ ->{
            sistema = new Sistema(0,1,FILAS,COLUMNAS);
            juego_terminado = false;
            start(stage);
            popup.close();
        });
        boton_2.setOnAction( _ -> System.exit(0));

        popupRoot.setStyle(DISENIO_POPUP);

        Scene popupScene = new Scene(popupRoot, 400, 200);
        popup.setScene(popupScene);

        popupBotones.getChildren().addAll(boton_1, boton_2);
        popup.setOnCloseRequest(Event::consume);
        popup.showAndWait();
    }
    public static void main(String[] args) {
        Application.launch();
    }
}