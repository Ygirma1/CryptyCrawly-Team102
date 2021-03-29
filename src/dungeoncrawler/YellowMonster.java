package dungeoncrawler;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class YellowMonster extends Monster {
    private int damage = 1;

    public YellowMonster(int width, int height, int health, ImagePattern img) {
        super(width, height, health, Color.YELLOW);
        this.setFill(img);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
