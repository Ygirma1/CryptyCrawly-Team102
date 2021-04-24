package dungeoncrawler.screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WinScreen {
    private final int width;
    private final int height;
    private final Text winText;
    private final Font winFont = new Font("High Tower Text", 55);
    Color lime;
    private final Font btFont = new Font("High Tower Text", 25);
    private final Button playButton;

    /**
     * No argument constructor for GameOverScreen.
     */
    public WinScreen() {
        this(500, 500);
    }

    /**
     * GameOverScreen constructor.
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public WinScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.winText = new Text("YOU WON");
        this.winText.setFont(winFont);
        this.winText.setFill(lime.rgb(50, 205, 50));
        this.playButton = new Button("Play again");
    }

    public Scene getScene() {
        Rectangle background = new Rectangle(this.width, this.height);
        background.setFill(Color.BLACK);
        this.winText.relocate(100, 150);

        this.playButton.setFont(btFont);
        this.playButton.setTextFill(Color.BLACK);
        this.playButton.setStyle("-fx-background-color: #32CD32;");
        this.playButton.setPrefWidth(150.0);
        this.playButton.setPrefHeight(50.0);
        this.playButton.relocate(175, 250);

        Pane pane = new Pane();
        pane.getChildren().addAll(background, winText, playButton);
        return new Scene(pane, this.width, this.height);
    }

    public Button getPlayButton() {
        return this.playButton;
    }
}
