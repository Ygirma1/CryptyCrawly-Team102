package dungeoncrawler.entity.potion;

import dungeoncrawler.entity.Player;

public class AttackPotion implements Potion {

    @Override
    public void applyEffect() {
        Player.setDamageModifier(Player.getDamageModifier() + 1);
    }

    @Override
    public String toString() {
        return "Attack Potion";
    }
}
