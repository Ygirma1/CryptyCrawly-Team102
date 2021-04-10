package dungeoncrawler.entity.monster;

import dungeoncrawler.entity.monster.Monster;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class PinkMonster extends Monster {
    private int damage = 1;

    public PinkMonster(int width, int height, int health, ImagePattern img) {
        super(width, height, health, Color.HOTPINK);
        this.setFill(img);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
