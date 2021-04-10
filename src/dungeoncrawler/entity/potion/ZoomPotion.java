package dungeoncrawler.entity.potion;

import dungeoncrawler.entity.Player;

// Maybe let this be a Rectangle so we can click and do stuff to it?
public class ZoomPotion implements Potion {

    @Override
    public void applyEffect() {
        // has to be static cause we create an instance of player for each room -> speed must be shared
        Player.setSpeed(Player.speed + 3);
    }

    @Override
    public String toString() {
        return "Zoom Potion";
    }
}
