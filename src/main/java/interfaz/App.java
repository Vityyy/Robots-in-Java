package interfaz;

import javafx.application.Application;
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
    private int CASILLA_ALTO = (ALTO_VENTANA - 100) / FILAS;

    //private int CASILLA_TAMAÑO = Math.min(ANCHO_CANVAS / COLUMNAS, ALTO_CANVAS / FILAS);

    @Override
    public void start(Stage stage) {

        Text titulo = new Text("Robots");
        Text stats = new Text("Level: pene vs Score: vagina");
        stats.setFill(Color.LIGHTGRAY);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        stats.setFont(Font.font("Arial", FontWeight.THIN, 25));

        VBox titulo_stats = new VBox();
        titulo_stats.getChildren().addAll(titulo, stats);
        titulo_stats.setAlignment(Pos.TOP_CENTER);
        titulo_stats.setPadding(new Insets(25, 0, 0, 0));
        titulo_stats.setSpacing(10);


        Button tp_aleatorio = new Button("Teleport Randomly");
        tp_aleatorio.setPrefSize(200,100);

        Button tp_seguro = new Button("Teleport Safely");
        tp_seguro.setPrefSize(200,100);

        Button no_moverse = new Button("Wait for Robots");
        no_moverse.setPrefSize(200,100);

        Button configuracion = new Button();

        StackPane root = new StackPane();
        StackPane pane = new StackPane();

        Scene scene = new Scene(root,ANCHO_VENTANA, ALTO_VENTANA, Color.BLACK);

        Canvas casilla_superior = new Canvas(ANCHO_CANVAS,200);
        Canvas casilla_inferior = new Canvas(ANCHO_CANVAS, 200);
        Canvas canvas = new Canvas(ANCHO_CANVAS, ALTO_CANVAS);

        StackPane.setAlignment(canvas, Pos.CENTER);
        StackPane.setAlignment(casilla_superior, Pos.TOP_CENTER);
        StackPane.setAlignment(casilla_inferior, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(tp_seguro, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(tp_aleatorio, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(no_moverse, Pos.BOTTOM_RIGHT);


        pane.getChildren().addAll(canvas, casilla_superior, casilla_inferior, tp_aleatorio, tp_seguro, no_moverse, titulo_stats);

        GraphicsContext barra_superior = casilla_superior.getGraphicsContext2D();
        GraphicsContext barra_inferior = casilla_inferior.getGraphicsContext2D();
        barra_superior.setFill(Color.SLATEGRAY);
        barra_inferior.setFill(Color.SLATEGRAY);
        barra_superior.fillRoundRect(0, 0, ANCHO_CANVAS , 150, 40, 40);;
        barra_inferior.fillRoundRect(0,50 , ANCHO_CANVAS, 150, 40, 40);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnMouseClicked((MouseEvent mouseEvent) -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            System.out.println("x: " + x + " y: " + y);
        });

        for (int x = 0; x <= COLUMNAS; x++) {
            for (int y = 0; y <= FILAS; y++) {
                if ((x + y) % 2 == 0) {
                    gc.setFill(Color.WHITE);
                } else {
                    gc.setFill(Color.GRAY);
                }
                gc.fillRect(x * CASILLA_ANCHO, y * CASILLA_ALTO, CASILLA_ANCHO, CASILLA_ALTO);
            }
        }

        root.getChildren().add(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}