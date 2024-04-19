package interfaz;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.Event;
public class Boton extends Button {
    private final EventHandler<ActionEvent> evento;
    public Boton(String nombre, EventHandler<ActionEvent> evento, double ancho, double alto) {
        super(nombre);
        this.evento = evento;
        this.setPrefSize(ancho, alto);
    }
    public EventHandler<ActionEvent> ActivarEvento(){
        return this.evento;
    }
}
