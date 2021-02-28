package DungeonCrawler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dungeon Crawler");
        initConfigScreen(); // Comment this out when you test your screen.
        //proceedToGameScreen();
    }

    private void initConfigScreen() {
        ConfigScreen configScreen = new ConfigScreen(width, height);
        Button proceedButton = configScreen.getProceedButton();
        proceedButton.setOnAction(e -> {
            this.characterName = configScreen.getNameField().getText();
            if (this.characterName == null || this.characterName.length() == 0 || this.characterName.strip().length() == 0) {
                configScreen.getNameField().setText("Please enter a character name.");
                return;
            }

            if (configScreen.getDifficultyDropdown().getValue() == null) {
                return;
            }
            this.difficulty = configScreen.getDifficultyDropdown().getValue().toString();
            if (configScreen.getWeaponDropdown().getValue() == null) {
                return;
            }
            this.weapon = configScreen.getWeaponDropdown().getValue().toString();
            System.out.println(this.difficulty);
            System.out.println(this.weapon);
            proceedToGameScreen();
        });
        this.primaryStage.setScene(configScreen.getScene());
        this.primaryStage.show();
    }

    private void proceedToGameScreen() {
        InitialGameScreen screen = new InitialGameScreen();
        this.primaryStage.setScene(screen.getScene());
        this.primaryStage.show();
    }

    public static String getDifficulty() {
        return (difficulty);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
