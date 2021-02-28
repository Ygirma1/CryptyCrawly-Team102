package DungeonCrawler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class InitialGameScreen {
    private int gold;

    public InitialGameScreen() {
        if (Controller.getDifficulty().equals("Easy")) {
            gold = 100;
        } else if (Controller.getDifficulty().equals("Medium")) {
            gold = 75;
        } else {
            gold = 50;
        }
    }
    public Scene getScene() {
        BorderPane root  = new BorderPane();
        Text roomName = new Text("Starting Room");
        Text goldAmount = new Text("" + gold);
        goldAmount.setFill(Color.DARKGOLDENROD);
        root.setLeft(roomName);
        root.setRight(goldAmount);

        Pane pane = new Pane();
        Button exit1 = new Button("Exit 1");
        Button exit2 = new Button("Exit 2");
        Button exit3 = new Button("Exit 3");
        Button exit4 = new Button("Exit 4");
        exit1.setPrefSize(50, 50);
        exit2.setPrefSize(50, 50);
        exit3.setPrefSize(50, 50);
        exit4.setPrefSize(50, 50);
        exit1.setLayoutX(100);
        exit1.setLayoutY(100);
        exit2.setLayoutX(350);
        exit2.setLayoutY(100);
        exit3.setLayoutX(100);
        exit3.setLayoutY(350);
        exit4.setLayoutX(350);
        exit4.setLayoutY(350);
        exit1.setBackground(new Background(new BackgroundFill(Color.LIME, null, null)));
        exit2.setBackground(new Background(new BackgroundFill(Color.LIME, null, null)));
        exit3.setBackground(new Background(new BackgroundFill(Color.LIME, null, null)));
        exit4.setBackground(new Background(new BackgroundFill(Color.LIME, null, null)));
        pane.getChildren().addAll(exit1, exit2, exit3, exit4);
        root.getChildren().add(pane);

        return new Scene(root, 500, 500);
    }

}
