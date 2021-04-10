package dungeoncrawler.screen;

import dungeoncrawler.Controller;
import dungeoncrawler.entity.Difficulty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GoldRoom extends Room {
    private Label instructionLabel;
    private final Button goldButton;
    private final Button goldButton2;
    private final Button goldButton3;
    private final Button exitButton;
    private final Text goldText;
    private final Text goldButtonText;
    private final Text goldButton2Text;
    private final Text goldButton3Text;
    private final Color goldColor = Color.rgb(255, 215, 0);
    private int goldIncAmount;

    public GoldRoom(int width, int height, String id, Difficulty diff) {

        super(width, height, id, diff);

        this.goldText = new Text("Gold: " + Controller.getGold());
        this.goldText.setFont(new Font("High Tower Text", 19));
        this.goldText.setFill(goldColor);
        this.goldText.setX(420);
        this.goldText.setY(20);

        this.exitButton = new Button("Exit");
        this.exitButton.setLayoutX(450);
        this.exitButton.setLayoutY(450);
        this.exitButton.setPrefSize(50, 50);
        this.exitButton.setDisable(true);
        this.exitButton.setId("exitButton");

        this.instructionLabel = new Label();
        this.instructionLabel = new Label("You've found the gold room!");
        this.instructionLabel.setLayoutX(50);
        this.instructionLabel.setLayoutY(50);

        int gold = 25 * ((int) ((3 * Math.random()) + 1));
        this.goldButton = new Button();
        this.goldButton.setPrefSize(100, 100);
        this.goldButton.setLayoutY(170);
        this.goldButton.setLayoutX(50);
        this.goldButton.setId("goldButton");
        goldButtonText = new Text(gold + " gold");
        goldButtonText.setLayoutY(320);
        goldButtonText.setLayoutX(50);

        int gold2 = 25 * ((int) ((3 * Math.random()) + 1));
        this.goldButton2 = new Button();
        this.goldButton2.setPrefSize(100, 100);
        this.goldButton2.setLayoutY(170);
        this.goldButton2.setLayoutX(200);
        this.goldButton2.setId("goldButton2");
        goldButton2Text = new Text(gold2 + " gold");
        goldButton2Text.setLayoutY(320);
        goldButton2Text.setLayoutX(200);

        int gold3 = 25 * ((int) ((3 * Math.random()) + 1));
        this.goldButton3 = new Button();
        this.goldButton3.setPrefSize(100, 100);
        this.goldButton3.setLayoutY(170);
        this.goldButton3.setLayoutX(350);
        this.goldButton3.setId("goldButton3");
        goldButton3Text = new Text(gold3 + " gold");
        goldButton3Text.setLayoutY(320);
        goldButton3Text.setLayoutX(350);

        try {
            Image gold25Image = new Image(new FileInputStream(
                    System.getProperty("user.dir") + "\\res\\gold25.png"));
            ImageView imageView25 = new ImageView(gold25Image);
            imageView25.setFitWidth(100);
            imageView25.setFitHeight(100);
            Image gold50Image = new Image(new FileInputStream(
                    System.getProperty("user.dir") + "\\res\\gold50.png"));
            ImageView imageView50 = new ImageView(gold50Image);
            imageView50.setFitWidth(100);
            imageView50.setFitHeight(100);
            Image gold75Image = new Image(new FileInputStream(
                    System.getProperty("user.dir") + "\\res\\gold75.png"));
            ImageView imageView75 = new ImageView(gold75Image);
            imageView75.setFitWidth(100);
            imageView75.setFitHeight(100);
            this.goldButton.setGraphic(imageView25);
            this.goldButton2.setGraphic(imageView50);
            this.goldButton3.setGraphic(imageView75);
        } catch (FileNotFoundException exception) {
            System.out.println("Gold image not found " + exception);
        }

        goldButton.setOnAction(e -> {
            goldIncAmount = gold;
            Controller.setGold(Controller.getGold() + gold);
            goldText.setText("Gold: " + Controller.getGold());
            goldButton.setDisable(true);
            if (goldButton.isDisable() && goldButton2.isDisable() && goldButton3.isDisable()) {
                exitButton.setDisable(false);
            }
        });

        goldButton2.setOnAction(e -> {
            goldIncAmount = gold2;
            Controller.setGold(Controller.getGold() + gold2);
            goldText.setText("Gold: " + Controller.getGold());
            goldButton2.setDisable(true);
            if (goldButton.isDisable() && goldButton2.isDisable() && goldButton3.isDisable()) {
                exitButton.setDisable(false);
            }
        });

        goldButton3.setOnAction(e -> {
            goldIncAmount = gold3;
            Controller.setGold(Controller.getGold() + gold3);
            goldText.setText("Gold: " + Controller.getGold());
            goldButton3.setDisable(true);
            if (goldButton.isDisable() && goldButton2.isDisable() && goldButton3.isDisable()) {
                exitButton.setDisable(false);
            }
        });
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.getChildren().addAll(this.instructionLabel, this.goldButton,
                this.goldButton2, this.goldButton3,
                this.exitButton, this.goldText, this.goldButtonText,
                this.goldButton2Text, this.goldButton3Text);
        return new Scene(pane, this.getWidth(), this.getHeight());
    }

    public Button getExitButton() {
        return this.exitButton;
    }

    public int getGoldIncAmount() {
        return this.goldIncAmount;
    }
}
