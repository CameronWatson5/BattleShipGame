/*
This is the Square Class which is used when creating a new board object.
The java.util.function.Consumer Class is used to update the board when a ship is sunk.
*/
import java.util.function.Consumer;

public class Square {
    /*
    These are the attributes associated with the Square Class.
    The y and x attributes determine the squares position.
    The content attribute is used when printing the board.
    The content attribute can have many possible values such as '-' for water, 'S' for smallShip,
    'M' for mediumShip, 'L' for largeShip, 'X' for hit, and '0' for miss.
     */
    private int y;
    private int x;
    private char content;
    private boolean isHit;
    private Battleship ship;
    private Consumer<Character> onShipSunkCallback;

    // this constructor is used when creating the board.
    public Square(int x, int y) {
        this.y = y;
        this.x = x;
        this.content = '-';
        this.isHit = false;
        this.ship = null;
        this.onShipSunkCallback = null;
    }
    // This is used to return a value to the Board class.
    public void setOnShipSunkCallback(Consumer<Character> callback) {
        this.onShipSunkCallback = callback;
    }


    // setContent() sets a char value to content.
    public void setContent(char content) {
        this.content = content;
    }
    // getContent returns the char value.
    public char getContent() {
        return this.content;
    }
    // isOccupied checks if a square is occupied by a ship.
    public boolean isOccupied() {
        return this.ship != null;
    }
    /*
    setShip() is called by the Board Class when it sets a ships position.
    The this.content = ship.getSize() is used to find the char to represent the ship on the board.
     */

    public void setShip(Battleship ship) {
        this.ship = ship;
        this.content = ship.getSize();
    }

    public boolean isHit() {
        return this.isHit;
    }
    /*
    hit() sets the content in a square to 'X' if the square isOccupied is true.
    hit() also calls the ship.hit() method to reduce the health of the ship and check if it has been sunk.
    Else it will setMiss().
     */
    public void hit() {
        if (this.isOccupied()) {
            this.content = 'X';
            this.ship.hit();
            if (this.ship.isSunk() && this.onShipSunkCallback != null) {
                this.onShipSunkCallback.accept(this.ship.getSize());
            }
        } else {
            this.setMiss();
        }

        this.isHit = true;
    }
    // setMiss() sets the content of a square to '0'
    public void setMiss() {
        this.content = '0';
        this.isHit = true;
    }
    // getOccupant() returns who occupies a square.
    public Battleship getOccupant() {
        return this.ship;
    }
}