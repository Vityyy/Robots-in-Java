package logica;

public class RobotComplejo implements Enemigo{
    private boolean funcional = true;
    public void moverse(Grilla grilla) {
        if (!this.funcional) {
            return;
        }
        int[] posicion_actual = grilla.getPosicionEnemigo(this);
        int[] coordenadas_jugador = grilla.getPosicionJugador();
        int[] nueva_posicion = new int[]{posicion_actual[0], posicion_actual[1]};

        for (int i = 0; i < 2; i++) {
            nueva_posicion[0] = Integer.compare(coordenadas_jugador[0], posicion_actual[0]);
            nueva_posicion[1] = Integer.compare(coordenadas_jugador[1], posicion_actual[1]);
        }
        grilla.setPosicionEnemigo(this, nueva_posicion);
    }
    public void setNoFuncional(){
        this.funcional = false;
    }
}
