package interfaz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Setup {
    private Setup() {
    }

    public static VBox inicializar_textos(int nivel, int score, String NOMBRE_JUEGO, String FONT) {
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

    public static Boton[] inicializar_botones(int tps_seguros, String BOTON_ALEATORIO, String BOTON_SEGURO, String BOTON_ESPERAR, String BOTON_TAMANIO, int ANCHO_VENTANA) {
        Boton tp_aleatorio = new Boton(STR."\{BOTON_ALEATORIO} (O)",ANCHO_VENTANA / 3,140);
        Boton tp_seguro = new Boton(String.format("%s: %d (P)", BOTON_SEGURO, tps_seguros),ANCHO_VENTANA / 3,140);
        Boton no_moverse = new Boton(STR. "\{BOTON_ESPERAR} (S)",ANCHO_VENTANA / 3,140);
        Boton tamanio = new Boton(STR."\{BOTON_TAMANIO} (O)",ANCHO_VENTANA / 8,40);
        return new Boton[]{tp_aleatorio, tp_seguro, no_moverse, tamanio};
    }

    public static VBox textos_fin(Text mensaje_top, Text mensaje_bot, String FONT) {
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
    public static Object[] creacion_popup() {
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
    public static Object[] popup_tamanio_aux(String BOTON_ACEPTAR, String DISENIO_POPUP) {
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
        Scene popupScene = new Scene(popupRoot, 400, 300);

        popupRoot.getChildren().addAll(mensaje_superior, filas_input, columnas_input, boton);
        popupRoot.setStyle(DISENIO_POPUP);
        popup.setScene(popupScene);

        return new Object[]{popup, boton, filas_input, columnas_input};
    }

    public static Object[] finalizar_juego_aux(String TEXTO_FIN, String BOTON_REINICIAR, String BOTON_SALIR, String DISENIO_POPUP, String FONT, int nivel, int score) {
        Object[] popups = creacion_popup();
        Stage popup = (Stage) popups[0];
        VBox popupRoot = (VBox) popups[1];
        HBox popupBotones = (HBox) popups[2];

        Text game_over = new Text(TEXTO_FIN);
        Text mensaje = new Text(String.format("Level: %d | Score: %d", nivel, score));

        Boton boton_1 = new Boton(BOTON_REINICIAR, 100, 50);
        Boton boton_2 = new Boton(BOTON_SALIR, 100, 50);

        VBox textos = Setup.textos_fin(game_over, mensaje, FONT);
        popupRoot.getChildren().addAll(popupBotones, textos);
        popupRoot.setStyle(DISENIO_POPUP);

        Scene popupScene = new Scene(popupRoot, 400, 200);
        popup.setScene(popupScene);

        popupBotones.getChildren().addAll(boton_1, boton_2);

        return new Object[]{popup, boton_1, boton_2};
    }
}