package dungeoncrawler;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class GreenMonster extends Monster {
    private int damage = 1;

    public GreenMonster(int width, int height, int health, ImagePattern img) {
        super(width, height, health, Color.LIMEGREEN);
        this.setFill(img);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
