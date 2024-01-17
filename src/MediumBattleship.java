/*
This is the MediumBattleship subclass. The constructor is used when placing ships on the board.
The size is set to char 'M', so that it can be represented on the board.
The length and health are set to 2.
*/
public class MediumBattleship extends Battleship {
    private static final char size = 'M';
    private static final int length = 4;
    private static final int health = 4;

    public MediumBattleship() {
        super(size, length, health);
    }
}

