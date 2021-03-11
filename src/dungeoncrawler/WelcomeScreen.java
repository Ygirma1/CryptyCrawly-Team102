package dungeoncrawler;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WelcomeScreen {
    private final int width;
    private final int height;
    private final Color backgroundColor = Color.rgb(120, 135, 135);
    private final Button playButton;
    private final Text gameTitle;
    private final Text authorText1;
    private final Text authorText2;

    /**
     * No-parameter constructor for Welcome Screen.
     */
    public WelcomeScreen() {
        this(500, 500);
    }

    /**
     * The WelcomeScreen constructor.
     * @param width Determines how wide the welcome screen is
     * @param height Determines how tall the welcome screen is
     */
    public WelcomeScreen(int width, int height) {
        this.width = width;
        this.height = height;

        Font titleFont = new Font("High Tower Text", 60);
        Color textColor = Color.rgb(48, 54, 54);
        Font smallFont = new Font("High Tower Text", 24);

        //init title text
        this.gameTitle = new Text("Crypt of Treasure");
        this.gameTitle.setFont(titleFont);
        this.gameTitle.setFill(textColor);

        //init author text
        this.authorText1 = new Text("Aaron Mallory, Chuong Dong, Tristan Rogers,");
        this.authorText1.setFont(smallFont);
        this.authorText1.setFill(textColor);

        this.authorText2 = new Text("Nishant Baglodi, and Yafet Girma");
        this.authorText2.setFont(smallFont);
        this.authorText2.setFill(textColor);


        //init start button
        this.playButton = new Button("Start");
        this.playButton.setFont(smallFont);
        this.playButton.setStyle("-fx-background-color: #a1abab;");
        this.playButton.setPrefWidth(100.0);
        this.playButton.setPrefHeight(50.0);
    }

    /**
     * A getter method for the scene associated with the game's welcome screen.
     * @return The scene containing all nodes for the welcome screen.
     */
    public Scene getScene() {
        //Used a HBox in case additional buttons are desired
        HBox buttonBox = new HBox(this.playButton);
        buttonBox.setSpacing(50.0);
        buttonBox.setAlignment(Pos.CENTER);

        //VBox to add author text
        VBox authorVBox = new VBox(authorText1, authorText2);
        authorVBox.setSpacing(15);
        authorVBox.setAlignment(Pos.CENTER);

        //VBox to add title and buttonBox
        VBox titleVBox = new VBox(this.gameTitle, buttonBox, authorVBox);
        titleVBox.setSpacing(50.0);
        titleVBox.setAlignment(Pos.CENTER);

        //StackPane to add background and main vBox
        Rectangle background = new Rectangle(this.width, this.height, this.backgroundColor);
        StackPane sPane = new StackPane(background, titleVBox);

        return new Scene(sPane, this.width, this.height);
    }

    /**
     * Getter method for the play button on the welcome screen.
     * @return The button that switches the scene from the welcome screen to the config screen.
     */
    public Button getPlayButton() {
        return this.playButton;
    }

    /**
     * Width getter for the welcome screen.
     * @return int value for screen width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Height getter for the welcome screen.
     * @return int value for screen height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for the Text object associated with the game's title.
     * @return The text object representing the game title.
     */
    public Text getGameTitle() {
        return this.gameTitle;
    }
}
