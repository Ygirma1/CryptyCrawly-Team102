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
        this(500,500);
    }


    public InventoryScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.weapons = Player.getWeaponInventory();
        this.potions = Player.getPotionInventory();
        this.quantities = Player.getInventoryQuantity();
        this.items = new Button[6];

        //init empty buttons
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            if (i < 3) {
                items[i] = new Button(weapons[i].getName());
                items[i].setOnAction(e -> {
                    Player.updateWeapon(Player.getWeaponInventory()[finalI]);
                });
            } else {
                items[i] = new Button(potions[i - 3].toString());
                items[i].setOnAction(e -> {
                   potions[finalI].applyEffect();
                   quantities[finalI]--;
                   if (quantities[finalI] <= 0) {
                       items[finalI].setDisable(true);
                   }
                });
            }
            items[i].setPrefSize(90, 70);
            if (quantities[i] <= 0) {
                items[i].setDisable(true);
            }

        }

        /*
        for (int i = 0; i < 3; i++) {
            items[i] = new Button(weapons[i].getName());
            items[i].setPrefSize(90, 70);

            if (potions != null) {
                Potion potion = potions[i];
                if (potion != null) {
                    items[i + 3] = new Button(potion.toString());
                    items[i + 3].setId("itemsButton" + (i + 3));
                    items[i + 3].setPrefSize(90, 70);
                    items[i + 3].setOnAction(e -> {
                        potion.applyEffect();
                        int index = Character.getNumericValue(((Button)e.getSource()).getId().charAt(11));
                        // Remove, only use each potion once
                        Player.getPotionInventory()[index - 3] = null;
                        this.items[index].setDisable(true);
                    });
                }
            }
        }

         */

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
