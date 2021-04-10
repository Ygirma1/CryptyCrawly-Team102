package dungeoncrawler.screen;

import dungeoncrawler.entity.Weapon;
import dungeoncrawler.entity.Player;
import dungeoncrawler.entity.potion.Potion;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class InventoryScreen {
    private final Weapon[] weapons;
    private final Potion[] potions;
    private final int[] quantities;
    private Button backButton;
    private Player player;
    private final Button[] items;
    private final int width;
    private final int height;

    public InventoryScreen() {
        this(500,500, null);
    }

    public InventoryScreen(Player player) {
        this(500, 500, player);
    }

    public InventoryScreen(int width, int height, Player player) {
        this.width = width;
        this.height = height;
        this.weapons = Player.getWeaponInventory();
        this.potions = Player.getPotionInventory();
        this.quantities = Player.getInventoryQuantity();
        this.items = new Button[6];

        for (int i = 0; i < 3; i++) {
            items[i] = new Button(weapons[i].getName());
            items[i].setPrefSize(90, 70);

//            if (potions != null) { //TODO delete line after implementing potions
//                items[i + 3] = new Button(potions[i].toString()); //TODO update name of potions
//                items[i + 3].setPrefSize(90, 70);
//            }
        }

        if (potions != null) {
            items[3] = new Button(potions[0].toString());
            items[3].setPrefSize(90, 70);
            items[3].setOnAction(e -> {
                potions[0].applyEffect(player);
            });
        }

        this.backButton = new Button("Back");
        this.backButton.setLayoutX(450);
        this.backButton.setLayoutY(450);
        this.backButton.setPrefSize(50, 50);
        this.backButton.setId("backButton");
    }
//    − Inventory can be implemented either as a separate screen or as a panel on the
//    game screen.
//            • If you choose the separate screen implementation, you must also
//    include a way to return to the game screen.
//            − The inventory should list all the player’s items. At the start of the game, it
//    should contain the starting weapon chosen by the player.

    //alter player inventory in player constructor
    public Scene getScene() {
        VBox weaponBox = new VBox();
        for (Button button: items) {
            if (button != null) {
                weaponBox.getChildren().add(button);
            }
        }
        Pane pane = new Pane();
        pane.getChildren().addAll(weaponBox, this.backButton);

        return new Scene(pane, width, height);
    }

    public Button[] getItems() {
        return this.items;
    }


    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }
}
