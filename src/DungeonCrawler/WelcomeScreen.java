package DungeonCrawler;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class WelcomeScreen {
    private int width;
    private int height;
    private Button playButton;
    private Button settingsButton;
    private Text gameTitle;

    public WelcomeScreen(){
        this(500, 500);
    }

    /**
     * The WelcomeScreen constructor
     * @param width Determines how wide the welcome screen is
     * @param height Determines how tall the welcome screen is
     */
    public WelcomeScreen(int width, int height) {
        this.width = width;
        this.height = height;
        this.gameTitle = new Text("Game Title");
        this.playButton = new Button("Start");
    }

    /**
     * A getter method for the scene associated with the game's welcome screen.
     * @return The scene containing all nodes for the welcome screen.
     */
    public Scene getScene(){
        HBox buttonBox = new HBox(this.playButton);
        //Used a HBox in case additional buttons are desired
        buttonBox.setSpacing(50.0);
        buttonBox.setAlignment(Pos.CENTER);
        this.playButton.setPrefWidth(85.0);this.playButton.setPrefHeight(35.0);
        VBox titleVBox = new VBox(this.gameTitle, buttonBox);
        titleVBox.setSpacing(50.0);
        this.gameTitle.setFont(Font.font("Times New Roman", 75.0));
        this.gameTitle.setWrappingWidth(1000.0);
        this.gameTitle.setTextAlignment(TextAlignment.CENTER);
        titleVBox.setAlignment(Pos.CENTER);
        Scene main = new Scene(titleVBox, width, height);
        return main;
    }

    /**
     * Getter method for the play button on the welcome screen.
     * @return The button that switches the scene from the welcome screen to the config screen.
     */
    public Button getPlayButton(){
        return this.playButton;
    }

    /**
     * Width getter for the welcome screen.
     * @return int value for screen width
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Height getter for the welcome screen.
     * @return int value for screen height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Getter for the Text object associated with the game's title.
     * @return The text object representing the game title.
     */
    public Text getGameTitle(){
        return this.gameTitle;
    }



}

