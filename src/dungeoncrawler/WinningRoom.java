package dungeoncrawler;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class WinningRoom extends Room {
    private final Label winningMessageLabel;

    public WinningRoom(int width, int height, int numberOfRooms) {
        super(width, height, numberOfRooms, "Winning");
        this.winningMessageLabel = new Label("YOU WIN!!!");
        this.winningMessageLabel.setFont(new Font("Comic Sans MS", 20));
        this.winningMessageLabel.setLayoutX(150);
        this.winningMessageLabel.setLayoutY(100);
    }


    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.getChildren().addAll(this.winningMessageLabel);
        return new Scene(pane, this.getWidth(), this.getHeight());
    }

}
