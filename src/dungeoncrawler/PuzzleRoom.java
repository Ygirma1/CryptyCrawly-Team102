package dungeoncrawler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


import java.util.ArrayList;

public class PuzzleRoom extends Room {
    private Label instructionLabel;
    private Label puzzleLabel;
    private ArrayList<Button> puzzleButtons;
    private Button exitButton;

    public PuzzleRoom(int width, int height, Difficulty diff) {
        super(width, height, "Puzzle", diff);
        this.exitButton = new Button("Exit");
        this.exitButton.setId("exit");
        this.exitButton.setLayoutX(450);
        this.exitButton.setLayoutY(200);
        this.exitButton.setPrefSize(50, 50);
        this.exitButton.setDisable(true);

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
        this.puzzleLabel.setId("Question1");
        this.puzzleLabel.setLayoutX(175);
        this.puzzleLabel.setFont(new Font("Comic Sans MS", 20));

        this.puzzleButtons.get(0).setText("1");
        this.puzzleButtons.get(1).setText("2 ... duh");
        this.puzzleButtons.get(1).setId("Correct1");
        this.puzzleButtons.get(2).setText("idk");

        this.puzzleButtons.get(1).setOnAction(e -> {
            questionTwoSetUp();
        });

        this.puzzleButtons.get(0).setOnAction(e -> {
            // do nothing
        });
        this.puzzleButtons.get(2).setOnAction(e -> {
            // do nothing
        });
    }

    private void questionThreeSetUp() {
        this.puzzleLabel.setText("Should we have a week of spring break?");
        this.puzzleLabel.setId("Question3");
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
                this.instructionLabel.setId("Winner!");
                this.instructionLabel.setLayoutX(140);
                this.puzzleLabel.setText("");

                this.exitButton.setDisable(false);
            });
        }
    }

    private void questionTwoSetUp() {
        this.puzzleLabel.setText("What is the sum of all positive number");
        this.puzzleLabel.setId("Question2");
        this.puzzleLabel.setLayoutX(100);
        this.puzzleLabel.setFont(new Font("Comic Sans MS", 17));

        this.puzzleButtons.get(0).setText("-1/12");
        this.puzzleButtons.get(1).setText("Infinity");
        this.puzzleButtons.get(1).setId("Correct2");
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
        pane.getChildren().addAll(this.exitButton, this.instructionLabel, this.puzzleLabel);
        for (Button button : this.puzzleButtons) {
            pane.getChildren().addAll(button);
        }
        return new Scene(pane, this.getWidth(), this.getHeight());
    }


    public Button getExitButton() {
        return this.exitButton;
    }
}
