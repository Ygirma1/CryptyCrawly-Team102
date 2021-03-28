package dungeoncrawler;

import javafx.scene.paint.Color;

public class YellowMonster extends Monster {
    private int damage = 1;

    public YellowMonster(int width, int height, int health) {
        super(width, height, health, Color.YELLOW);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
