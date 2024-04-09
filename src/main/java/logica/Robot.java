package logica;

public class Robot implements Movimiento {
    private boolean funcional = true;
    private final boolean complejo;

    public Robot(boolean es_complejo){
        this.complejo = es_complejo;
    }
    public void moverse(int[] coordenadas, Grilla grilla){
        int cant_movimientos = this.complejo ? 2 : 1;
        posicion_actual = grilla.getPosicion();
        var nueva_posicion = new int[] {posicion_actual[0],posicion_actual[1]};

        if (!this.funcional){
            return;
        } else{
            for (int i = 0; i < cant_movimientos; i++){
                nueva_posicion[0]=Integer.compare(coordenadas[0],posicion_actual[0]);
                nueva_posicion[1]=Integer.compare(coordenadas[1],posicion_actual[1]);
            }
        }
    }
    public void destruir(){

    }
}
