package logica;

public class RobotComplejo implements Enemigo{
    private boolean funcional = true;
    public int[] moverse(Grilla grilla) {
        int[] posicion_actual = grilla.getPosicionEnemigo(this);
        if (!this.funcional) {
            return posicion_actual;
        }
        int[] coordenadas_jugador = grilla.getPosicionJugador();
        int[] nueva_posicion = new int[]{posicion_actual[0], posicion_actual[1]};

        for (int i = 0; i < 2; i++) {
            nueva_posicion[0] += Integer.compare(coordenadas_jugador[0], nueva_posicion[0]);
            nueva_posicion[1] += Integer.compare(coordenadas_jugador[1], nueva_posicion[1]);
        }
        return nueva_posicion;
    }
    public void setNoFuncional(){
        this.funcional = false;
    }
    public boolean getFuncional(){return this.funcional; }
}
