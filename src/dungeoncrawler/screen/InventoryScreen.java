package dungeoncrawler.screen;

import dungeoncrawler.entity.Weapon;
import dungeoncrawler.entity.Player;
import dungeoncrawler.entity.potion.Potion;
import javafx.geometry.Pos;
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

        //init item buttons
        //buttons are static, quantities[item index] determines if usable
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
                   potions[finalI - 3].applyEffect();
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
        VBox inventory = new VBox();
        for (Button button: items) {
            inventory.getChildren().add(button);
        }

        VBox shop = new VBox();
        Button shopButton = new Button("SHOP TEXT");
        shopButton.setPrefSize(90, 70);
        shop.getChildren().add(shopButton);

        HBox columns = new HBox();
        columns.setAlignment(Pos.CENTER);
        columns.setSpacing(100);
        columns.getChildren().addAll(inventory, shop);

        HBox exit = new HBox();
        exit.setAlignment(Pos.CENTER);
        exit.getChildren().add(backButton);

        BorderPane bp = new BorderPane();
        bp.setCenter(columns);
        bp.setBottom(exit);

        return new Scene(bp, width, height);
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
