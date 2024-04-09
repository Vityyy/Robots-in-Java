package logica;

public class Jugador implements Movimiento{

    private boolean vivo;

    public Jugador() {
    }
    @Override
    public void moverse( int[] coordenadas){
        posicion_actual = Grilla.getPosicionJugador();

        int[] nueva_posicion = {posicion_actual[0],posicion_actual[1]};
        nueva_posicion[0] += Integer.compare(coordenadas[0],posicion_actual[0]);
        nueva_posicion[1] += Integer.compare(coordenadas[1],posicion_actual[1]);
        
        Grilla.setPosicionJugador(nueva_posicion);
    }
}
