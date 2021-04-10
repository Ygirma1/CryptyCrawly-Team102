package dungeoncrawler.entity.potion;

import dungeoncrawler.entity.Player;

public class HealthPotion implements Potion {
    @Override
    public void applyEffect() {
        Player.setHealth(Player.getHealth() + 10);
    }

    @Override
    public String toString() {
        return "Health Potion";
    }
}
