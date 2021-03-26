package dungeoncrawler;

import javafx.scene.paint.Color;

public class GreenMonster extends Monster {
    int health = 3;
    int damage = 1;

    public GreenMonster(int radius) {
        super(radius, Color.LIMEGREEN);
    }
}
