package logica;

public class RobotSimple implements Enemigo{
    private boolean funcional = true;
    public void moverse(Grilla grilla) {
        if (!this.funcional) {
            return;
        }
        posicion_actual = grilla.getPosicionEnemigo(this);
        coordenadas_jugador = grilla.getPosicionJugador();
        var nueva_posicion = new int[]{posicion_actual[0], posicion_actual[1]};
        nueva_posicion[0] = Integer.compare(coordenadas_jugador[0], posicion_actual[0]);
        nueva_posicion[1] = Integer.compare(coordenadas_jugador[1], posicion_actual[1]);
    }
    public void setNoFuncional(){
        this.funcional = false;
    }
}

