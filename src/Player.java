/*
This is the Player Class, it has a player constructor which takes in a name and a board.
This class has the fireShot(), getName(), and takeTurn() methods.
This class has the attributes of name and board.
*/

//Scanner is used when inputting the targetX and the targetY values.

import java.util.Scanner;

public class Player {
    private final String name;
    private final Board<Battleship> board;
    private final Scanner scanner;

    // This is the player constructor, which is used in the Main Class.
    public Player(String name, Board<Battleship> board) {
        this.name = name;
        this.board = board;
        this.scanner = new Scanner(System.in);
    }
    /*
    The fireShot() method takes in the targetX, targetY, and the opponent's board.
    The fireShot() method checks if a square has been hit and if a ship occupies a square.
    Then it returns the result.
    */
    public ShotResult fireShot(int targetY, int targetX, Board<Battleship> opponentBoard) {
        Square targetSquare = opponentBoard.getSquare(targetY, targetX);
        if (targetSquare.isHit()) {
            return Player.ShotResult.REPEAT_SHOT;
        } else {
            targetSquare.hit();
            if (targetSquare.isOccupied()) {
                Battleship hitShip = targetSquare.getOccupant();
                return hitShip.isSunk() ? Player.ShotResult.SUNK : Player.ShotResult.HIT;
            } else {
                return Player.ShotResult.MISS;
            }
        }
    }
    // The getName() method returns the specific name requested.
    public String getName() {
        return this.name;
    }
    /*
    The takeTurn() method is called from the Game Class.
    The takeTurn() method has been passed the opponent's name and board.
    This is so that the player attacks the other player's board and not their own board.
    The class informs the player that it is their turn and also prints the opponent's hidden board.
    Then the player needs to input the x and y coordinates to attack the enemy.
    This is checked.
    Regardless of the result, the player receives a message which is printed to the screen.
    If a result is HIT, MISS, or SUNK, then the hidden board is reprinted to the screen.
     */
    public void takeTurn(Player opponent, Board<Battleship> opponentBoard) {
        System.out.println("Player " + this.name + ", it's your turn.");
        System.out.println("Here is your opponent " + opponent.getName() + "'s hidden board");
        opponentBoard.printHiddenBoard(opponent, opponentBoard);

        while (true) { // Loop until valid input is received
            System.out.print("Enter coordinates between 0-9 (x, y), separated with a space: ");
            String input = this.scanner.nextLine();
            String[] parts = input.trim().split("\\s+");

            if (parts.length == 2) {
                try {
                    int targetX = Integer.parseInt(parts[0]);
                    int targetY = Integer.parseInt(parts[1]);

                    if (targetX >= 0 && targetX < 10 && targetY >= 0 && targetY < 10) {
                        ShotResult shotResult = this.fireShot(targetY, targetX, opponentBoard);
                        switch (shotResult) {
                            case HIT:
                                System.out.println("You hit " + opponent.getName() + "'s ship! Here is " + opponent.getName() + "'s updated board.");
                                opponentBoard.printHiddenBoard(opponent, opponentBoard);
                                return;
                            case MISS:
                                System.out.println("Your shot missed. Here is " + opponent.getName() + "'s updated board.");
                                opponentBoard.printHiddenBoard(opponent, opponentBoard);
                                return;
                            case REPEAT_SHOT:
                                System.out.println("You have already targeted this square. Please choose another square.");
                                break;
                            case SUNK:
                                System.out.println("You sunk " + opponent.getName() + "'s ship! Here is " + opponent.getName() + "'s updated board.");
                                opponentBoard.printHiddenBoard(opponent, opponentBoard);
                                return;
                            default:
                                System.out.println("An unexpected error occurred. Please try again.");
                                break;
                        }
                    } else {
                        System.out.println("Invalid coordinates. Please enter numbers between 0 and 9.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numeric coordinates only.");
                }
            } else {
                System.out.println("Incorrect number of coordinates. Please enter exactly two numbers separated by a space.");
            }
        }
    }

    public static enum ShotResult {
        HIT,
        MISS,
        REPEAT_SHOT,
        SUNK;
    }
}