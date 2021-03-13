package dungeoncrawler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class Room {
    private ArrayList<Button> exits;
    private Button bLeft;
    private Button bRight;
    private Button bUp;
    private Button bDown;
    private Text id; // just a label for a room, use for debugging
    private String roomID; // maybe we need this to keep track of the room position?
    private static int roomCount;
    private int exitNum;
    private Room left;
    private Room right;
    private Room up;
    private Room down;
    private int width;
    private int height;

    public Room(int width, int height, int numberOfRooms) {
        this(width, height, numberOfRooms, "doge");
    }

    public Room(String id) {
        this(500, 500, 4, id);
    }

    public Room(int width, int height, int numberOfRooms, String id) {
        this.id = new Text(id);
        this.exits = new ArrayList<>();
        this.bLeft = new Button("left");
        this.exits.add(bLeft);
        this.bRight = new Button("right");
        this.exits.add(bRight);
        this.bUp = new Button("up");
        this.exits.add(bUp);
        this.bDown = new Button("down");
        this.exits.add(bDown);
        for (Button exit : this.exits) {
            exit.setPrefSize(50, 50);
        }

        this.width = width;
        this.height = height;
        this.exitNum = numberOfRooms;
    }

    public void generateMap(Room startingRoom) {
        Room rRoom = new Room("right");
        startingRoom.right = rRoom;
        rRoom.setLeft(startingRoom);
        Room lRoom = new Room("left");
        startingRoom.left = lRoom;
        lRoom.setRight(startingRoom);
        Room uRoom = new Room("up");
        startingRoom.up = uRoom;
        uRoom.setDown(startingRoom);
        Room dRoom = new Room("down");
        startingRoom.down = dRoom;
        dRoom.setUp(startingRoom);
    }

    // When extends this class, override this to set your room's own scene
    public Scene getScene() {
        Pane pane = new Pane();
        if (exits.get(0) != null) {
            exits.get(0).setLayoutX(100);
            exits.get(0).setLayoutY(200);
            pane.getChildren().add(exits.get(0));
        }
        if (exits.get(1) != null) {
            exits.get(1).setLayoutX(300);
            exits.get(1).setLayoutY(200);
            pane.getChildren().add(exits.get(1));
        }
        if (exits.get(2) != null) {
            exits.get(2).setLayoutX(200);
            exits.get(2).setLayoutY(100);
            pane.getChildren().add(exits.get(2));
        }
        if (exits.get(3) != null) {
            exits.get(3).setLayoutX(200);
            exits.get(3).setLayoutY(400);
            pane.getChildren().add(exits.get(3));
        }
        id.setLayoutY(100);
        pane.getChildren().add(id);
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

    public int getExitNum() {
        return this.exitNum;
    }

    public void setRight(Room room) {
        this.right = room;
    }

    public Room getRight() {
        return this.right;
    }

    public Room getLeft() {
        return this.left;
    }

    public Room getUp() {
        return this.up;
    }

    public Room getDown() {
        return this.down;
    }

    public void setLeft(Room room) {
        this.left = room;
    }

    public void setUp(Room room) {
        this.up = room;
    }

    public void setDown(Room room) {
        this.down = room;
    }

    public Button getBRight() {
        return this.bRight;
    }

    public Button getBLeft() {
        return this.bLeft;
    }

    public Button getBUp() {
        return this.bUp;
    }

    public Button getBDown() {
        return this.bDown;
    }
}
