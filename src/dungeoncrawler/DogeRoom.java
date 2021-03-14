package dungeoncrawler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DogeRoom extends Room {
    private Label instructionLabel;
    private Label instructionLabel2;
    private Button dogeButton;
    private int dogeCounter;
    private Button exitButton;


    public DogeRoom(int width, int height, String id, Difficulty diff) {

        super(width, height, id, diff);

        this.exitButton = new Button("Exit");
        this.exitButton.setLayoutX(450);
        this.exitButton.setLayoutY(200);
        this.exitButton.setPrefSize(50, 50);
        this.exitButton.setDisable(true);
        this.exitButton.setId("exitButton");

        this.instructionLabel = new Label();
        this.instructionLabel = new Label("You have been captured by the doge!");
        this.instructionLabel.setFont(new Font("Comic Sans MS", 20));
        this.instructionLabel.setLayoutX(80);
        this.instructionLabel.setLayoutY(100);

        this.instructionLabel2 = new Label();
        this.instructionLabel2 = new Label("Press on it 5 times to escape!");
        this.instructionLabel2.setFont(new Font("Comic Sans MS", 20));
        this.instructionLabel2.setLayoutX(120);
        this.instructionLabel2.setLayoutY(130);


        this.dogeButton = new Button();
        this.dogeButton.setPrefSize(200, 200);
        this.dogeButton.setLayoutY(170);
        this.dogeButton.setLayoutX(150);
        this.dogeButton.setId("dogeButton");
        try {
            Image dogeImage = new Image(new FileInputStream(System.getProperty("user.dir")
                    + "\\res\\doge.png"));
            ImageView imageView = new ImageView(dogeImage);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            this.dogeButton.setGraphic(imageView);
        } catch (FileNotFoundException exception) {
            System.out.println("rip " + exception);
        }
        this.dogeCounter = 0;
        this.dogeButton.setOnAction(e -> {
            this.dogeCounter++;
            if (this.dogeCounter >= 5) {
                this.dogeButton.setDisable(true);
                this.instructionLabel2.setText("");
                this.instructionLabel.setText("You have escaped from the doge!");
                this.instructionLabel.setLayoutX(100);
                this.instructionLabel.setLayoutY(100);

                this.exitButton.setDisable(false);

            }
        });
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.getChildren().addAll(this.instructionLabel, this.dogeButton,
                this.instructionLabel2, this.exitButton);
        return new Scene(pane, this.getWidth(), this.getHeight());
    }

    public Button getExitButton() {
        return this.exitButton;
    }
}
