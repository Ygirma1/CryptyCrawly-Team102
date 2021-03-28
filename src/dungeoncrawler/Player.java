package dungeoncrawler;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    private static int health = 5;
    private static int damage = 1;
    private boolean goNorth;
    private boolean goSouth;
    private boolean goEast;
    private boolean goWest;
    private static boolean alive = true;

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);
        this.setVisible(true);
        this.setFill(Color.RED);
    }

    public void move() {
        int dx = 0;
        int dy = 0;
        if (goNorth) {
            dy = -5;
        }
        if (goWest) {
            dx = -5;
        }
        if (goSouth) {
            dy = 5;
        }
        if (goEast) {
            dx = 5;
        }

        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);


    }

    public void takeDamage(int damageCount) {
        health -= damageCount;
        if (health <= 0) {
            alive = false;
        }
    }

    public static boolean isAlive() {
        return alive;
    }

    public static void setHealth(int newHealth) {
        health = newHealth;
    }

    public static void setIsAlive(boolean isAlive) {
        alive = isAlive;
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

    public static int getHealth() { return health; }
}
