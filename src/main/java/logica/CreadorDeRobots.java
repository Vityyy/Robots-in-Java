package logica;
public class CreadorDeRobots {
    /**
     * Inicializa los robots del juego según el nivel en el que se encuentra el jugador.
     * @param nivel nivel actual
     * @return Robot[]
     */
    public static Robot[] crearRobots(int nivel, int n_fil, int n_col) {
        int maxRobots = (n_fil * n_col * nivel) / 60;
        if (maxRobots < 2) {
            maxRobots = 1 + nivel;
        }
        Robot[] robots = new Robot[maxRobots];
        int cantSimples = (maxRobots * 2) / 4;
        for(int i = 0; i < cantSimples; i++) {
            var robot = new Robot();
            robots[i] = robot;
        }
        for(int j = cantSimples; j< maxRobots; j++) {
            var robot = new RobotComplejo();
            robots[j] = robot;
        }
        return robots;
    }
}