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
    private String characterName = "";
    private String difficulty = "";
    private String weapon = "";


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dungeon Crawler");
        initConfigScreen(); // Comment this out when you test your screen.
    }

    private void initConfigScreen() {
        ConfigScreen configScreen = new ConfigScreen(width, height);
        Button proceedButton = configScreen.getProceedButton();
        proceedButton.setOnAction(e -> {
            this.characterName = configScreen.getNameField().getText();
            if (this.characterName == null || this.characterName.length() == 0 || this.characterName.strip().length() == 0) {
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
        // TODO: create the initial game scene class, add that scene here, setScene for the stage, call show to change screen
    }

    public static void main(String[] args) {
        launch(args);
    }
}
