/*
This is the SmallBattleship subclass. The constructor is used when placing ships on the board.
The size is set to char 'S', so that it can be represented on the board.
The length and health are set to 1.
 */
public class SmallBattleship extends Battleship {
    private static final char size = 'S';
    private static final int length = 3;
    private static final int health = 3;

    public SmallBattleship() {
        super(size, length, health);
    }
}
