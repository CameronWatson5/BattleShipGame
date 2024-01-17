/*
This is the Board Class, and it has many methods and attributes.
This class initializes and creates the boards for the players to use.
After initializing the board, 3 smallShips, 2 mediumShips, and 1 largeShips are added randomly to the board.
This is printed to the screen to show the positions of each ship.
This Class also has the printHiddenBoard() method which hides the ship locations.
*/

// the Random class is used to place ships randomly.
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board<S extends Battleship> {
    private int smallShipsSunk = 0;
    private int mediumShipsSunk = 0;
    private int largeShipsSunk = 0;
    private List<Battleship> ships = new ArrayList<>();
    private static final int boardSize = 10;
    private Square[][] board = new Square[boardSize][boardSize];
    private String playerName;
    private char shipSize;
    private Random random = new Random();

    // getRandomRow() is used when placing a ship's coordinates randomly.
    private int getRandomRow() {
        return this.random.nextInt(boardSize);
    }
    // getRandomCol() is used when placing a ship's coordinates randomly.
    private int getRandomCol() {
        return this.random.nextInt(boardSize);
    }
    // getRandomBoolean() is used when placing a ship's direction randomly.
    private boolean getRandomBoolean() {
        return this.random.nextBoolean();
    }
    // this is used in the Main class when creating a board for each player.
    public Board(String playerName) {
        this.playerName = playerName;
    }
    // the printBoard() method prints the full board without hiding the ships locations.
    public void printBoardWithShipsVisible() {
        for(int i = 0; i < boardSize; ++i) {
            for(int j = 0; j < boardSize; ++j) {
                char cell = this.board[i][j].getContent();
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    // the initializeBoard() method populates the board with '-' char values.
    private void initializeBoard() {
        for(int row = 0; row < boardSize; ++row) {
            for(int col = 0; col < boardSize; ++col) {
                Square square = new Square(row, col);
                square.setOnShipSunkCallback(this::handleShipSunk);
                this.board[row][col] = square;
            }
        }
    }
    // This method finds the ship char value and uses it to decrease the type of ship it's associated with.
    private void handleShipSunk(Character size) {
        switch (size) {
            case 'L':
                ++this.largeShipsSunk;
                break;
            case 'M':
                ++this.mediumShipsSunk;
                break;
            case 'S':
                ++this.smallShipsSunk;
        }
    }
    /*
    The placeShips() method uses a while loop which keeps trying to place a ship until 3 smallShips,
    2 mediumShips, and 1 largeShip are placed on the board.
    */
    public void placeShips() {
        int smallShipsCount = 3;
        int mediumShipsCount = 2;
        int largeShipsCount = 1;

        while(smallShipsCount > 0) {
            Battleship smallShip = new SmallBattleship();
            if (this.tryToPlaceShip(smallShip)) {
                smallShipsCount--;
            }
        }
        while(mediumShipsCount > 0) {
            Battleship mediumShip = new MediumBattleship();
            if (this.tryToPlaceShip(mediumShip)) {
                mediumShipsCount--;
            }
        }
        while(largeShipsCount > 0) {
            Battleship largeShip = new LargeBattleship();
            if (this.tryToPlaceShip(largeShip)) {
                largeShipsCount--;
            }
        }
    }
    /*
    The tryToPlaceShip method generates random coordinates for a ship and determines if a ship can be placed or not.
    It returns a boolean related to this.
    */
    private boolean tryToPlaceShip(Battleship ship) {
        for(int attempts = 0; attempts < 100; ++attempts) {
            int startRow = this.getRandomRow();
            int startCol = this.getRandomCol();
            boolean isVertical = this.getRandomBoolean();
            if (this.setShip(startRow, startCol, ship, isVertical)) {
                return true;
            }
        }
        return false;
    }
    /*
    The setShip() method gets the ship's length and a ship's symbol.
    These symbols are the same as the ship size char value.
    It calls the isValidPlacement method to check if a ship can be placed.
    */
    public boolean setShip(int row, int col, Battleship ship, boolean isVertical) {
        int shipLength = ship.getLength();
        char shipSymbol = ship.getSize();
        if (!this.isValidPlacement(row, col, shipLength, isVertical)) {
            return false;
        } else {
            int i;
            if (isVertical) {
                for(i = row; i < row + shipLength; ++i) {
                    this.board[i][col].setContent(shipSymbol);
                    this.board[i][col].setShip(ship);
                }
            } else {
                for(i = col; i < col + shipLength; ++i) {
                    this.board[row][i].setContent(shipSymbol);
                    this.board[row][i].setShip(ship);
                }
            }

            this.ships.add(ship);
            return true;
        }
    }
    /*
    The isValidPlacement() method checks if a ship will fit on the board by seeing if the
    length + (row OR column) is greater than the board size.
    This makes sure the ships are correctly placed.
    */
    private boolean isValidPlacement(int startRow, int startCol, int shipLength, boolean isVertical) {
        int i;
        if (!isVertical) {
            if (startCol + shipLength > boardSize) {
                return false;
            }

            for(i = startCol; i < startCol + shipLength; ++i) {
                if (this.board[startRow][i].getContent() == 'S' || this.board[startRow][i].getContent() == 'M' || this.board[startRow][i].getContent() == 'L') {
                    return false;
                }
            }
        } else {
            if (startRow + shipLength > boardSize) {
                return false;
            }

            for(i = startRow; i < startRow + shipLength; ++i) {
                if (this.board[i][startCol].getContent() == 'S' || this.board[i][startCol].getContent() == 'M' || this.board[i][startCol].getContent() == 'L') {
                    return false;
                }
            }
        }
        return true;
    }
    /*
    This method counts the instances of the SmallBattleship class.
    This is essential for determining when the game ends, as it is deducted from ships sunk to get ships remaining.
    */
    public int countSmallShips() {
        int count = 0;
        for (Battleship ship : this.ships) {
            if (ship instanceof SmallBattleship) {
                count++;
            }
        }
        return count;
    }
    /*
    This method counts the instances of the MediumBattleship class.
    This is essential for determining when the game ends, as it is deducted from ships sunk to get ships remaining.
    */
    public int countMediumShips() {
        int count = 0;
        for (Battleship ship : this.ships) {
            if (ship instanceof MediumBattleship) {
                count++;
            }
        }
        return count;
    }
    /*
    This method counts the instances of the LargeBattleship class.
    This is essential for determining when the game ends, as it is deducted from ships sunk to get ships remaining.
    */
    public int countLargeShips() {
        int count = 0;
        for (Battleship ship : this.ships) {
            if (ship instanceof LargeBattleship) {
                count++;
            }
        }
        return count;
    }
    /*
    The createPlayerBoard() method is called by the Main Class.
    It uses the initializeBoard() and placeShips() methods to create a board and then populate it.
    Then it counts the number of small, medium, and large ships and prints this to the screen.
    Then it prints the name of the Player who owns this board, and prints the board with the ships visible.
    The reason why I print the ships on the board is to test that the game works.
    */
    public void createPlayerBoard() {
        // Initialize the board and place the ships
        this.initializeBoard();
        this.placeShips();

        // Count the number of each type of ship
        int smallShipCount = this.countSmallShips();
        int mediumShipCount = this.countMediumShips();
        int largeShipCount = this.countLargeShips();

        // Print the starting counts for each type of ship
        System.out.println(this.getPlayerName() + "'s Starting small ships: " + smallShipCount);
        System.out.println(this.getPlayerName() + "'s Starting medium ships: " + mediumShipCount);
        System.out.println(this.getPlayerName() + "'s Starting large ships: " + largeShipCount);

        // Print the player's board to the console
        System.out.println("Player " + this.getPlayerName() + "'s board with ships visible:");
        //this.printBoardWithShipsVisible();
    }
    // getPlayerName is used when printing the name to the console.
    public String getPlayerName() {
        return this.playerName;
    }

    // the method getSquare is used in the Player Class.
    Square getSquare(int targetRow, int targetCol) {
        return this.board[targetRow][targetCol];
    }
    /*
    This countShips() method is used to print the number of each ship remaining.
    It does this by first count the instances of each ship, then deducting it from ships of the same type
    which have been sunk.
    */
    public String countShips() {
        int smallShipCount = this.countSmallShips() - this.getSmallShipsSunk();
        int mediumShipCount = this.countMediumShips() - this.getMediumShipsSunk();
        int largeShipCount = this.countLargeShips() - this.getLargeShipsSunk();
        return "Small ships: " + smallShipCount + "; Medium Ships: " + mediumShipCount + "; Large Ships: " + largeShipCount;
    }
    // This method returns the number of smallShips sunk.
    public int getSmallShipsSunk() {
        return this.smallShipsSunk;
    }

    // This method returns the number of mediumShips sunk.
    private int getLargeShipsSunk() {
        return this.largeShipsSunk;
    }

    // This method returns the number of largeShips sunk.

    private int getMediumShipsSunk() {
        return this.mediumShipsSunk;
    }

    /*
    The printHiddenBoard() method is used to hide the location of the ships.
    Small ships are represented as 'S' on the board.
    Medium ships are represented as 'M' on the board.
    Large ships are represented as 'L' on the board.
    These are replaced with the cell '-', which is gotten.
    Additionally, the number of remaining ships is printed after the board is printed.
     */
    public void printHiddenBoard(Player opponent, Board<Battleship> opponentBoard) {
        String opponentName = opponent.getName();

        for(int i = 0; i < boardSize; ++i) {
            for(int j = 0; j < boardSize; ++j) {
                char cell = this.board[i][j].getContent();
                if (cell == 'X' || cell == '0') {
                    System.out.print(cell + " ");
                } else {
                    System.out.print("- ");
                }
            }

            System.out.println();
        }

        String shipCounts = this.countShips();
        System.out.println(opponentName + "'s Remaining ships --> " + shipCounts);
    }
    /*
    The allShipSunk() method checks how many of each ship is sunk.
    When 3 or more small ships are sunk AND 2 or more medium ships are sunk AND 1 or more large ship is sunk.
    This method will return true. This will end the game and print the winner.
    */
    public boolean allShipsSunk() {
        return this.smallShipsSunk >= 3 && this.mediumShipsSunk >= 2 && this.largeShipsSunk >= 1;
    }
}