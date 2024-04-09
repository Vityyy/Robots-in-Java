package logica;

public class Sistema {

    private final int n_fil = 45;
    private final int n_col = 45;
    private int Score;
    private int Tps_seguros;
    private Grilla grilla;

    public Sistema() {
        this.Score = 0;
        this.Tps_seguros = 2;
        this.grilla = Grilla(n_fil, n_col);
    }

    public int getN_fil() {
        return n_fil;
    }
    public int getN_col() {
        return n_col;
    }
    public int getScore() {
        return Score;
    }
    public int getTps_seguros() {
        return Tps_seguros;
    }
}
