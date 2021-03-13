package dungeoncrawler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import java.util.ArrayList;

public class Room {
    private ArrayList<Button> exits;
    private int roomID; // maybe we need this to keep track of the room position?
    private int width;
    private int height;

    public Room() {
        this(500, 500, 4);
    }

    public Room(int width, int height, int numberOfRooms) {
        if (numberOfRooms < 1 || numberOfRooms > 4) {
            numberOfRooms = 1;
        }
        this.exits = new ArrayList<>();
        for (int i = 0; i < numberOfRooms; i++) {
            this.exits.add(new Button("exit " + (i + 1)));
        }
        for (Button exit : this.exits) {
            exit.setPrefSize(50, 50);
        }

        this.width = width;
        this.height = height;
    }

    // When extends this class, override this to set your room's own scene
    public Scene getScene() {
        exits.get(0).setLayoutX(100);
        exits.get(0).setLayoutY(100);

        exits.get(1).setLayoutX(350);
        exits.get(1).setLayoutY(100);

        exits.get(2).setLayoutX(100);
        exits.get(2).setLayoutY(350);

        exits.get(3).setLayoutX(350);
        exits.get(3).setLayoutY(350);

        Pane pane = new Pane();
        pane.getChildren().addAll(exits.get(0), exits.get(1), exits.get(2), exits.get(3));
        return new Scene(pane, this.width, this.height);
    }

    public void setExitPos(int index, int x, int y) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }
        this.exits.get(index).setLayoutX(x);
        this.exits.get(index).setLayoutY(y);
    }

    public void setExitText(int index, String text) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setText(text);
    }

    public void setExitSize(int index, int width, int height) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setPrefSize(width, height);
    }

    public void setExitEventHandler(int index, EventHandler<ActionEvent> eventHandler) {
        if (index < 0 || index >= this.exits.size()) {
            return;
        }

        this.exits.get(index).setOnAction(eventHandler);
    }

    public ArrayList<Button> getExits() {
        return exits;
    }

    public void setExits(ArrayList<Button> exits) {
        this.exits = exits;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
