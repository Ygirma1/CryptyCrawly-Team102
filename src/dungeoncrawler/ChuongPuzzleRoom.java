package dungeoncrawler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class ChuongPuzzleRoom extends Room {
    private Label instructionLabel;
    private Label puzzleLabel;
    private ArrayList<Button> puzzleButtons;

    public ChuongPuzzleRoom(int width, int height, int numberOfRooms) {
        super(width, height, numberOfRooms);
        this.setExitPos(0, 225, 0);
        this.setExitPos(1, 450, 200);
        this.setExitPos(2, 225, 450);
        this.setExitPos(3, 0, 200);

        this.setExitText(0, "North");
        this.setExitText(1, "East");
        this.setExitText(2, "South");
        this.setExitText(3, "West");

        for (Button exit: this.getExits()) {
            exit.setDisable(true);
        }

        this.instructionLabel = new Label("To exit, answer these 3 questions correctly!");
        this.instructionLabel.setFont(new Font("Comic Sans MS", 20));
        this.instructionLabel.setLayoutX(50);
        this.instructionLabel.setLayoutY(100);

        this.puzzleLabel = new Label();
        this.puzzleLabel.setLayoutX(100);
        this.puzzleLabel.setLayoutY(200);
        this.puzzleButtons = new ArrayList<>();
        int x = 140;
        for (int i = 0; i < 3; i++) {
            this.puzzleButtons.add(new Button());
            this.puzzleButtons.get(i).setPrefSize(60, 30);
            this.puzzleButtons.get(i).setLayoutY(300);
            this.puzzleButtons.get(i).setLayoutX(x);
            x += 80;
        }
        questionOneSetUp();
    }

    private void questionOneSetUp() {
        this.puzzleLabel.setText("What is 1 + 1?");

        this.puzzleLabel.setLayoutX(175);
        this.puzzleLabel.setFont(new Font("Comic Sans MS", 20));

        this.puzzleButtons.get(0).setText("1");
        this.puzzleButtons.get(1).setText("2 ... duh");
        this.puzzleButtons.get(2).setText("idk");

        this.puzzleButtons.get(1).setOnAction(e -> {
            questionTwoSetUp();
        });
    }

    private void questionThreeSetUp() {
        this.puzzleLabel.setText("Should we have a week of spring break?");
        this.puzzleLabel.setLayoutX(100);
        this.puzzleLabel.setFont(new Font("Comic Sans MS", 17));

        this.puzzleButtons.get(0).setText("Yes");
        this.puzzleButtons.get(1).setText("Yes!!");
        this.puzzleButtons.get(2).setPrefSize(140, 30);
        this.puzzleButtons.get(2).setText("Yes please I'm dying");

        for (Button button : this.puzzleButtons) {
            button.setOnAction(e -> {
                for (Button exit : this.getExits()) {
                    exit.setDisable(false);
                }
                for (Button puzzleButton : this.puzzleButtons) {
                    puzzleButton.setDisable(true);
                    puzzleButton.setText("");
                }
                this.instructionLabel.setText("YEET You can proceed now!");
                this.instructionLabel.setLayoutX(140);
                this.puzzleLabel.setText("");
            });
        }
    }

    private void questionTwoSetUp() {
        this.puzzleLabel.setText("What is the sum of all positive number");
        this.puzzleLabel.setLayoutX(100);
        this.puzzleLabel.setFont(new Font("Comic Sans MS", 17));

        this.puzzleButtons.get(0).setText("-1/12");
        this.puzzleButtons.get(1).setText("Infinity");
        this.puzzleButtons.get(2).setText("69");

        this.puzzleButtons.get(0).setOnAction(e -> {
            questionThreeSetUp();
        });
        this.puzzleButtons.get(1).setOnAction(e -> {
            questionOneSetUp();
        });

        this.puzzleButtons.get(2).setOnAction(e -> {
            questionOneSetUp();
        });
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.getChildren().addAll(this.getExits().get(0), this.getExits().get(1),
                this.getExits().get(2), this.getExits().get(3),
                this.instructionLabel, this.puzzleLabel);
        for (Button button : this.puzzleButtons) {
            pane.getChildren().addAll(button);
        }
        return new Scene(pane, this.getWidth(), this.getHeight());
    }
}
