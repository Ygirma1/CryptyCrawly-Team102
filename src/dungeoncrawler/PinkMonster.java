package dungeoncrawler;

import javafx.scene.paint.Color;

public class PinkMonster extends Monster {
    int damage = 1;

    public PinkMonster(int width, int height, int health) {
        super(width, height, health, Color.HOTPINK);
    }
}
