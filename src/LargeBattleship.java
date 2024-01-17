/*
This is the LargeBattleship subclass. The constructor is used when placing ships on the board.
The size is set to char 'L', so that it can be represented on the board.
The length and health are set to 3.
*/
public class LargeBattleship extends Battleship {
    private static final char size = 'L';
    private static final int length = 5;
    private static final int health = 5;

    public LargeBattleship() {
        super(size, length, health);
    }
}
