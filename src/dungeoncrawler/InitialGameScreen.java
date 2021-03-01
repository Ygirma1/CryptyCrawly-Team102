package dungeoncrawler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.assertj.core.internal.Diff;

public class InitialGameScreen {
    private int gold;
    private int width;
    private int height;
    private Color backgroundColor = Color.rgb(120, 135, 135);

    public InitialGameScreen() {
        this(Difficulty.EASY, 500,500);
    }

    public InitialGameScreen(Difficulty difficulty, int width, int height) {
        this.width = width;
        this.height = height;
        if (difficulty.equals(Difficulty.EASY)) {
            gold = 100;
        } else if (difficulty.equals(Difficulty.MEDIUM)) {
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
        //pane.setBackground(bg, );

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

    public int getGold() {
        return gold;
    }
}