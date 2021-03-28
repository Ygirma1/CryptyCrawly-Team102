package dungeoncrawler;

import javafx.scene.paint.Color;

public class GreenMonster extends Monster {
    int damage = 1;

    public GreenMonster(int width, int height, int health) {
        super(width, height, health, Color.LIMEGREEN);
    }
}
