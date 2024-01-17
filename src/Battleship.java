/*
This is the Battleship Superclass, it is abstract because the Battleship Class is never initiated and is instead used
to give attributes and methods to subclasses.
This class has attributes and methods which are
inherited by the SmallBattleship, MediumBattleship, and LargeBattleship subclasses.
*/

public abstract class Battleship {
    /*
    These are the attributes associated with the ship class.
    The count attribute keeps track of amount of ships of each type on the board.
    When count for all ships is 0, then the game is over.
    The size attribute is used when printing the ships to screen after creating the board for each player.
    The size attribute is also associated with length in the Board Class.
    The health attribute keeps track of the health of each ship.
     */
    private int count;
    private final char size;
    private final int length;
    private int health;


    //This is the Ship constructor, which is used in the Board Class when placing ships.
    public Battleship(char size, int length, int health) {
        this.size = size;
        this.length = length;
        this.health = health;
    }

    //This is called by the hit() to decrease the count of a ship type object.
    protected void minusCount() {
        if (this.count > 0) {
            --this.count;
        }

    }
    //getSize() is used to pass the char attribute associated with a ship type.

    public char getSize() {
        return this.size;
    }

    //getLength() returns the length of the ship, this is used when placing ships on the board.

    public int getLength() {
        return this.length;
    }

    /*
    hit() decreases a ship objects health by one and checks if it is sunk.
    This method also checks if a ship is sunk using the isSunk() method.
    If the ship is sunk, it reduces the ship count using the decrementCount() method.
    */
    public void hit() {
        if (this.health > 0) {
            --this.health;
            if (this.isSunk()) {
                this.minusCount();
            }
        }
    }
    //isSunk() checks if a ship has been sunk by evaluating if its health is less than or equal to zero.
    public boolean isSunk() {
        return this.health <= 0;
    }
}