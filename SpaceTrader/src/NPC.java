import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

abstract class NPC {
    protected String name;
    protected int credits;
    protected Ship ship;

    protected Stage stage;
    protected Scene scene;
    protected String message;
    protected Player player;
    protected Universe universe;
    protected Region destination;
    protected Random rand;

    public NPC(Universe u, Region d, Player p) {
        universe = u;
        player = p;
        destination = d;
        rand = new Random();
        stage = new Stage();
    }

    abstract void encounter();

    public int getCredits() {
        return credits;
    }

    public void setCredits(int c) {
        credits = c;
    }

    public Ship getShip() {
        return ship;
    }

}