/*
This is the Game Class which is called by the Main Class.
This class has 2 methods, a start() method and checkGameFinish() method.
The start() method is called at the end of the Main Class.
The start() method alternates between player 1 and player 2 until all the ships are sunk.
This is achieved by checking the checkGameFinish() method.
The checkGameFinish() method checks if all the ships on a board are sunk.
If all ships are sunk, then it returns a true boolean value which ends the loop and ends the game.
The winning player's name is then printed to the console.
*/
public class Game {
    private final Board<Battleship> board1;
    private final Board<Battleship> board2;
    private final Player player1;
    private final Player player2;
    private boolean isFinished = false;

    // This is the game constructor, it takes in each player and each of their boards.
    public Game(Player player1, Board<Battleship> board1, Player player2, Board<Battleship> board2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board1 = board1;
        this.board2 = board2;
    }
    /*
    The start() method uses the takeTurn() method inside the Player class to fire a shot at the enemy's board.
    The while loop will keep running until isFinished is set to true by the checkGameFinish() method.
    Then it will print the name of the winning player.
    This is achieved through the getName() method inside the Player class.
    */
    public void start() {
        while(true) {
            if (!this.isFinished) {
                this.player1.takeTurn(this.player2, this.board2);
                if (this.checkGameFinish()) {
                    System.out.println(this.player1.getName() + " wins!");
                } else {
                    this.player2.takeTurn(this.player1, this.board1);
                    if (!this.checkGameFinish()) {
                        continue;
                    }

                    System.out.println(this.player2.getName() + " wins!");
                }
            }

            return;
        }
    }
    /*
    The checkGameFinish() method checks if allShipsSunk() is true for either board1 or board2.
    If either returns true, then checkGameFinish() returns true, and the game ends.
    Otherwise, it returns false, and the game continues.
    */
    public boolean checkGameFinish() {
        if (this.board1.allShipsSunk()) {
            isFinished = true;
        } else if (this.board2.allShipsSunk()) {
            isFinished = true;
        }
        return isFinished;
    }
}