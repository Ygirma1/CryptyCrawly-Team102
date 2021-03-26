package dungeoncrawler;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
    static int health = 5;
    static int damage = 1;
    boolean goNorth, goSouth, goEast, goWest;

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
        this.setVisible(true);
        this.setFill(Color.RED);
    }

    public void move() {
        int dx = 0;
        int dy = 0;
        if (goNorth) { dy = -3; }
        if (goWest) { dx = -3; }
        if (goSouth) { dy = 3; }
        if (goEast) { dx = 3; }

        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    public void setGoNorth(boolean goNorth) {
        this.goNorth = goNorth;
    }

    public void setGoSouth(boolean goSouth) {
        this.goSouth = goSouth;
    }

    public void setGoEast(boolean goEast) {
        this.goEast = goEast;
    }

    public void setGoWest(boolean goWest) {
        this.goWest = goWest;
    }

    public int getDamage() {
        return damage;
    }
}
