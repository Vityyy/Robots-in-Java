package interfaz;

import javafx.scene.control.TextField;
import logica.Sistema;
import java.util.HashMap;
/**
* Clase que se encarga de responder a cada tipo de evento
 */
public class Evento {
    private HashMap<String, int[]> mapeoMovimiento;
    private final static String TECLA_TP_ALEATORIO = "O";
    private final static String TECLA_TP_SEGURO = "P";
    private boolean tp_accionado = false;

    public Evento() {
        inicializarTeclasMovimiento();
    }
    private void inicializarTeclasMovimiento(){
        this.mapeoMovimiento = new HashMap<>();
        mapeoMovimiento.put("S",new int[]{0, 0});
        mapeoMovimiento.put("W",new int[]{-1, 0});
        mapeoMovimiento.put("A",new int[]{0, -1});
        mapeoMovimiento.put("D",new int[]{0, 1});
        mapeoMovimiento.put("X",new int[]{1, 0});
        mapeoMovimiento.put("Q",new int[]{-1, -1});
        mapeoMovimiento.put("E",new int[]{-1, 1});
        mapeoMovimiento.put("Z",new int[]{1, -1});
        mapeoMovimiento.put("C",new int[]{1, 1});
    }
    public boolean teclaAccionada(Sistema sistema, String tecla){
        if (mapeoMovimiento.containsKey(tecla)){
            var movimiento = mapeoMovimiento.get(tecla);
            var coor_jugador = sistema.getPosicionJugador();
            var coordenadas = new int[]{coor_jugador[0]+movimiento[0],coor_jugador[1]+movimiento[1]};
            return sistema.jugarTurno(coordenadas);
        } else if (tecla.equals(TECLA_TP_ALEATORIO)){
            return sistema.jugarTpAleatorio();
        } else if (tecla.equals(TECLA_TP_SEGURO)){
            botonTpActivado(sistema);
        }
        return false;
    }
    public boolean mouseAccionado(Sistema sistema, int[] coordenadas){
        if (tp_accionado){
            tp_accionado = false;
            return sistema.JugarTpSeguro(coordenadas);
        }
        return sistema.jugarTurno(coordenadas);
    }
    public void botonTpActivado(Sistema sistema){
        if (sistema.getTpsSeguros() > 0) {
            tp_accionado = true;
        }
    }
    public boolean botonTpAleatorioActivado(Sistema sistema){return sistema.jugarTpAleatorio();}
    public boolean botonNoMoverseActivado(Sistema sistema) {
        return sistema.jugarTurno(sistema.getPosicionJugador());
    }
    public Sistema RedimensionarJuego(Sistema sistema, String filasInput, String columnasInput) {
        if (filasInput == null || columnasInput==null)
            return sistema;
        try{
            int filas = Integer.parseInt(filasInput);
            int columnas = Integer.parseInt(columnasInput);
            if (filas>=4 && columnas>=4) {
                return new Sistema(0,1,filas,columnas);
            }
        }catch(NumberFormatException nfe){
            return sistema;
        }
        return sistema;
    }
}
