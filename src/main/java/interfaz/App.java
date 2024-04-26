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
    private static int FILAS = 6;
    private static int COLUMNAS = 6;
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
        int nivel = sistema.getNivel();
        int score = sistema.getScore();
        int tps_seguros = sistema.getTpsSeguros();

        Tablero tablero = new Tablero(FILAS, COLUMNAS, CASILLA_TAMANIO, ANCHO_CANVAS, ALTO_CANVAS);

        VBox root = new VBox();
        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA, Color.BLACK);
        StackPane casilla_superior = new StackPane();
        casilla_superior.setPrefHeight(TAMANIO_MENUES); casilla_superior.setStyle(DISENIO_SUPERIOR); casilla_superior.setAlignment(Pos.CENTER);
        VBox titulo_stats = Setup.inicializar_textos(nivel, score, NOMBRE_JUEGO, FONT);
        HBox tamanioBox = new HBox();
        Canvas canvas = tablero.ActualizarTablero(sistema.estadoJuego());

        HBox casilla_inferior = new HBox(5);
        casilla_inferior.setPrefHeight(TAMANIO_MENUES); casilla_inferior.setStyle(DISENIO_INFERIOR); casilla_inferior.setAlignment(Pos.CENTER);

        Boton[] botones = Setup.inicializar_botones(tps_seguros, BOTON_ALEATORIO, BOTON_SEGURO, BOTON_ESPERAR, BOTON_TAMANIO, ANCHO_VENTANA);
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

    private void popup_tamanio() {
        Object[] popups = Setup.popup_tamanio_aux(BOTON_ACEPTAR, DISENIO_POPUP);
        Stage popup = (Stage) popups[0]; Boton boton = (Boton) popups[1];
        TextField filas_input = (TextField) popups[2]; TextField columnas_input = (TextField) popups[3];

        boton.setOnAction( _ -> {
            String nuevas_filas = filas_input.getText();
            String nuevas_columnas = columnas_input.getText();
            sistema = Evento.RedimensionarJuego(sistema, nuevas_filas, nuevas_columnas);
            redimensionarConstantes();
            start(stage);
            popup.close();
        });

        popup.setOnCloseRequest(Event::consume);
        popup.showAndWait();
    }

    private void finalizar_juego(int nivel, int score) {
        Object[] popups = Setup.finalizar_juego_aux(TEXTO_FIN, BOTON_REINICIAR, BOTON_SALIR, DISENIO_POPUP, FONT, nivel, score);
        Stage popup = (Stage) popups[0]; Boton boton_1 = (Boton) popups[1]; Boton boton_2 = (Boton) popups[2];

        boton_1.setOnAction(_ ->{
            sistema = new Sistema(0,1,FILAS,COLUMNAS);
            juego_terminado = false;
            start(stage);
            popup.close();
        });
        boton_2.setOnAction( _ -> System.exit(0));

        popup.setOnCloseRequest(Event::consume);
        popup.showAndWait();
    }
    private void redimensionarConstantes(){
        FILAS = sistema.getDimension()[0]; COLUMNAS = sistema.getDimension()[1];
        CASILLA_TAMANIO = (ALTO_VENTANA - TAMANIO_MENUES * 2) / FILAS;
        ANCHO_VENTANA = CASILLA_TAMANIO * COLUMNAS;
        ANCHO_CANVAS = ANCHO_VENTANA;
        ALTO_CANVAS = CASILLA_TAMANIO * FILAS;
    }

    public static void main(String[] args) {
        Application.launch();
    }
}