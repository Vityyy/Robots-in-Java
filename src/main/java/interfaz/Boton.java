package interfaz;

import javafx.scene.control.Button;
public class Boton extends Button {
    public Boton(String nombre, double ancho, double alto) {
        super(nombre);
        this.setPrefSize(ancho, alto);
    }
}
