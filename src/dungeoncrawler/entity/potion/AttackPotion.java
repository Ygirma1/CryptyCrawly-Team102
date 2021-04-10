package dungeoncrawler.entity.potion;

import dungeoncrawler.entity.Player;

public class AttackPotion implements Potion {

    @Override
    public void applyEffect() {
        Player.setDamage(Player.getDamage() + 3);
    }

    @Override
    public String toString() {
        return "Attack Potion";
    }
}
