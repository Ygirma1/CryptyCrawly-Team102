package dungeoncrawler;

import javafx.scene.paint.Color;

public class YellowMonster extends Monster {
    int damage = 1;

    public YellowMonster(int width, int height, int health) {
        super(width, height, health, Color.YELLOW);
    }
}
