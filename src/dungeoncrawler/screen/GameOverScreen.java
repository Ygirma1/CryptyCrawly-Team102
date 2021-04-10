package dungeoncrawler.screen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameOverScreen {
    private final int width;
    private final int height;
    private final Text deathText;
    private final Font deathFont = new Font("High Tower Text", 55);
    private final Font btFont = new Font("High Tower Text", 25);
    private final Button playButton;

    /**
     * No argument constructor for GameOverScreen.
     */
    public GameOverScreen() {
        this(500, 500);
    }

    /**
     * GameOverScreen constructor.
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public GameOverScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.deathText = new Text("YOU DIED");
        this.deathText.setFont(deathFont);
        this.deathText.setFill(Color.DARKRED);
        this.playButton = new Button("Play again");
    }

    public Scene getScene() {
        Rectangle background = new Rectangle(this.width, this.height);
        background.setFill(Color.BLACK);
        this.deathText.relocate(100, 150);

        this.playButton.setFont(btFont);
        this.playButton.setTextFill(Color.BLACK);
        this.playButton.setStyle("-fx-background-color: #8B0000;");
        this.playButton.setPrefWidth(150.0);
        this.playButton.setPrefHeight(50.0);
        this.playButton.relocate(175, 250);

        Pane pane = new Pane();
        pane.getChildren().addAll(background, deathText, playButton);
        return new Scene(pane, this.width, this.height);
    }

    public Button getPlayButton() {
        return this.playButton;
    }
}
