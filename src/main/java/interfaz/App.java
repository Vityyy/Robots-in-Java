package interfaz;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;



/**
 * JavaFX App
 */
public class App extends Application {

    private int ANCHO_CANVAS = 1300;
    private int ALTO_CANVAS = 600;
    private int FILAS = 40;
    private int COLUMNAS = 40;
    private int CASILLA_ANCHO = ANCHO_CANVAS / COLUMNAS;
    private int CASILLA_ALTO = ALTO_CANVAS / FILAS;

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root,1300, 900, Color.BLACK);

        StackPane pane = new StackPane();
        final Canvas canvas = new Canvas(ANCHO_CANVAS, ALTO_CANVAS);
        pane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnMouseClicked((MouseEvent mouseEvent) -> {
            // Insertar aquí el código a ejecutar cuando se haga clic en el ratón
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