package dungeoncrawler;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller extends Application {
    private Stage primaryStage;
    private final int width = 500;
    private final int height = 500;
    private static String characterName = "";
    private static String difficulty = "";
    private static String weapon = "";


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dungeon Crawler");
<<<<<<< HEAD:src/DungeonCrawler/Controller.java
        initConfigScreen(); // Comment this out when you test your screen.
        //proceedToGameScreen();
=======
        //initConfigScreen(); // Comment this out when you test your screen.
        welcomeScreen(); //Comment this out as well when testing your screen.
    }

    private void welcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        Button play = welcomeScreen.getPlayButton();
        this.primaryStage.setScene(welcomeScreen.getScene());
        this.primaryStage.show();
        Button startButton = welcomeScreen.getPlayButton();
        startButton.setOnAction(e -> {
            initConfigScreen();
        });
>>>>>>> master:src/dungeoncrawler/Controller.java
    }

    private void initConfigScreen() {
        ConfigScreen configScreen = new ConfigScreen(width, height);
        Button proceedButton = configScreen.getProceedButton();
        proceedButton.setOnAction(e -> {
            // Checking if character name is valid
            this.characterName = configScreen.getNameField().getText();
            if (this.characterName == null || this.characterName.length() == 0
                    || this.characterName.strip().length() == 0) {
                configScreen.getNameField().setText("Please enter a character name.");
                return;
            }
            //Checking if selected difficulty is valid (not null)
            if (configScreen.getDifficultyRBGroup().getSelectedToggle() == null) {
                return;
            }
            this.difficulty = configScreen.getDifficulty().toString();
            //Checking if selected weapon is valid (not null)
            if (configScreen.getWeaponDropdown().getValue() == null) {
                return;
            }
            this.weapon = configScreen.getWeaponDropdown().getValue();
            proceedToGameScreen();
        });
        this.primaryStage.setScene(configScreen.getScene());
        this.primaryStage.show();
    }

    private void proceedToGameScreen() {
<<<<<<< HEAD:src/DungeonCrawler/Controller.java
        InitialGameScreen screen = new InitialGameScreen();
        this.primaryStage.setScene(screen.getScene());
        this.primaryStage.show();
    }

    public static String getDifficulty() {
        return (difficulty);
=======
        InitialGameScreen screen = new InitialGameScreen(difficulty);
        this.primaryStage.setScene(screen.getScene());
        this.primaryStage.show();
>>>>>>> master:src/dungeoncrawler/Controller.java
    }

    public static void main(String[] args) {
        launch(args);
    }
}
