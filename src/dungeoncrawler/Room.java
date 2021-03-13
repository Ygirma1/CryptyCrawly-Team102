package dungeoncrawler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private ArrayList<Button> exits;
    private Room[] adjRooms = new Room[4]; // ordered right, left, up, down
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
        rRoom.left = startingRoom;
        updateAdjRooms(rRoom);
        Room lRoom = new Room("left");
        startingRoom.left = lRoom;
        lRoom.right = startingRoom;
        updateAdjRooms(lRoom);
        Room uRoom = new Room("up");
        startingRoom.up = uRoom;
        uRoom.down = startingRoom;
        updateAdjRooms(uRoom);
        Room dRoom = new Room("down");
        startingRoom.down = dRoom;
        dRoom.up = startingRoom;
        updateAdjRooms(dRoom);
        updateAdjRooms(startingRoom);
        Random rand = new Random();
        Room next = startingRoom.adjRooms[rand.nextInt(3)];
        rGenerateMap(next, 0);
    }

    private void rGenerateMap(Room current, int roomDepth) {
        if (roomDepth >= 6) {
            generateBossRoom(current);
        } else {
            //TODO generate rest of map
            Room nextRoom = new Room("new"); //TODO get random room (not already in use)
            rGenerateMap(nextRoom, roomDepth + 1);
        }
    }

    private void generateBossRoom(Room current) {
        //TODO generate boss room (doge room?????)
    }

    private void updateAdjRooms(Room current) {
        this.adjRooms[0] = this.right;
        this.adjRooms[1] = this.left;
        this.adjRooms[2] = this.up;
        this.adjRooms[3] = this.down;
    }

    // When extends this class, override this to set your room's own scene
    public Scene getScene() {
        Pane pane = new Pane();
        if (this.right != null) {
            //right button
            exits.get(1).setLayoutX(300);
            exits.get(1).setLayoutY(200);
            pane.getChildren().add(exits.get(1));
        }
        if (this.left != null) {
            //left button
            exits.get(0).setLayoutX(100);
            exits.get(0).setLayoutY(200);
            pane.getChildren().add(exits.get(0));
        }
        if (this.up != null) {
            //up button
            exits.get(2).setLayoutX(200);
            exits.get(2).setLayoutY(100);
            pane.getChildren().add(exits.get(2));
        }
        if (this.down != null) {
            //down button
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
