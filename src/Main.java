/*
This is the Main Class. This Class sets up the game.
The Main method creates 2 player objects and 2 board objects which are associated with the Player Class.
The Main method then passes these objects to the Game Class and starts the game with the start() method.
*/

//Scanner is used to read input from Players.
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Scanner is used to read input from Player 1.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name for Player 1: ");
        //This input is stored in the String Player1Name variable.
        String player1Name = scanner.nextLine();

        //Scanner is used to read input from Player 2.
        System.out.print("Enter the name for Player 2: ");
        //This input is stored in the String Player2Name variable.
        String player2Name = scanner.nextLine();

        //Create board1 object which is associated with Player1Name. This board stores Battleship objects for player1.
        Board<Battleship> board1 = new Board<>(player1Name);
        //The createPlayerBoard method initializes, populates, and prints board1.
        board1.createPlayerBoard();

        //Create player1 object which is associated with board1.
        Player player1 = new Player(player1Name, board1);

        //Create board2 object which is associated with Player2Name. This board stores Battleship objects for player2.
        Board<Battleship> board2 = new Board<>(player2Name);
        //The createPlayerBoard method initializes, populates, and prints board2.
        board2.createPlayerBoard();

        //Create player2 object which is associated with board2.
        Player player2 = new Player(player2Name, board2);

        //Objects are passed to the Game Class and the game begins with the game.start() method.
        Game game = new Game(player1, board1, player2, board2);
        game.start();
    }
}