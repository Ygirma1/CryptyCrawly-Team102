package dungeoncrawler;

import javafx.scene.paint.Color;

public class PinkMonster extends Monster {
    private int damage = 1;

    public PinkMonster(int width, int height, int health) {
        super(width, height, health, Color.HOTPINK);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
