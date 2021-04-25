package dungeoncrawler.entity.monster;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class DogeMonster extends Monster {
    private int damage = 3;
    public DogeMonster(int width, int height, int health, ImagePattern img) {
        super(width, height, health, Color.WHITE);
        this.setFill(img);
        this.setId("Doge");
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
