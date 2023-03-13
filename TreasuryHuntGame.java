
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Al-Hazmi, Mojeeb Khaled Mohammed.
 * @author Rahsepar, Mohammad Ferdous
 * @see An instance of this class holds the state of a running match and the
 *      game logic.
 */
public class TreasuryHuntGame {

    final Board playerBoard;
    final Board villainBoard;
    public static Scanner scanner = new Scanner(System.in);
    private TreasuryHuntApp app = new TreasuryHuntApp();

    /**
     * Set to TRUE to keep the game loop running. Set to FALSE to exit.
     */
    boolean running;

    /**
     * When playing, enemy treasures should be hidden from the player.
     * Change below to FALSE for testing purposes during development of this
     * program.
     */
    private final boolean hideVillainShips = false;

    /**
     * File name in case won
     */
    public static final String WON = "won.txt";

    /**
     * File name in case lost
     */
    public static final String LOST = "lost.txt";

    /**
     * Creates a new game with new boards.
     */
    public TreasuryHuntGame() {
        playerBoard = new Board();
        villainBoard = new Board();
    }

    /**
     * Creates a game based on saved boards from a previous game.
     * 
     * @param playerBoard  player
     * @param villainBoard oppenent
     */
    public TreasuryHuntGame(Board playerBoard, Board villainBoard) {
        this.playerBoard = playerBoard;
        this.villainBoard = villainBoard;
    }

    /**
     * Main game loop. Keep running to play.
     * Interrupt the loop to get back to main menu.
     */
    public void run() {
        running = true;
        System.out.println("Spiel gestartet. Drücke ENTER während der Zieleingabe, um zum Hauptmenü zurückzukehren.");

        while (running) {
            playersTurn();
            if (running)
                villainsTurn();
        }

    }

    /**
     * Its player's turn
     */
    private void playersTurn() {

        String coordinate;
        int x = 0;
        int y = 0;
        int[] arrCoordinate;
        String found = "";

        try {
            do {
                System.out.println(found);
                System.out.println("Spieler ist am Zug.");
                villainBoard.print(hideVillainShips);

                System.out.println("Bitte Koordinaten eingeben. z.B. A1, B2 oder C5. Beachte dabei A-E und 1-5.");
                System.out.println();
                System.out.println("ENTER eingeben um zu pausieren.");
                coordinate = scanner.nextLine();

                if (coordinate.equalsIgnoreCase("")) {
                    running = false;
                    break;
                }

                arrCoordinate = convertCoordinatesToInt(coordinate);
                x = arrCoordinate[0];
                y = arrCoordinate[1];
                if (villainBoard.getField(x, y) == Board.TREASURE) {
                    found = "Schatz gefunden";
                    villainBoard.setFieldHit(x, y);
                }

            } while (!villainBoard.areAllTreasuresFound() && villainBoard.getField(x, y) != Board.EMPTY);

            try {
                if (villainBoard.areAllTreasuresFound()) {

                    printMassege(WON);
                    running = false;
                    pause();

                }
            } catch (Exception e) {
                System.out.println("DU HAST GEWONNEN!!!");
                running = false;
            }

            if (coordinate.equalsIgnoreCase("") == false) {
                if (villainBoard.getField(x, y) == Board.EMPTY) {
                    villainBoard.setFieldNO_TREASURE_FOUND(x, y);
                    System.out.println("Kein Schatz gefunden");
                    System.out.println();

                    villainBoard.print(hideVillainShips);
                    pause();
                }
            }

        } catch (Exception e) {
            System.out.println();
            System.out
                    .println("Nur Kombination aus einem Buchstaben zwischen A-E und einer Zahl zwischen 1-5 erlaubt.");
            playersTurn();
        }

    }

    /**
     * its opponent's turn
     */
    private void villainsTurn() {

        System.out.println("Gegner ist am Zug.");
        playerBoard.print(false);
        int[] villainSearch = getVillainSearch();

        playerBoard.print(true);
        pause();
    }

    /**
     * Asks the user to press ENTER to continue.
     * Can be called anywhere in the game to avoid too much output at once.
     */
    private void pause() {
        System.out.println();
        System.out.println("Drücke ENTER um fortzufahren...");
        System.out.println();
        new Scanner(System.in).nextLine();
    }

    /**
     * Gets an array with the two coordinates (x,y) the villain shoots at.
     */
    private int[] getVillainSearch() {
        int x;
        int y;
        int[] shot;

        // Strategy to aim a shot: Pick a random field that is empty
        do {
            x = new Random().nextInt(Board.BOARD_SIZE);
            y = new Random().nextInt(Board.BOARD_SIZE);

            shot = new int[] { x, y };
            System.out.println("Gegner sucht auf " + convertCoordinatesToString(shot));

            if (playerBoard.getField(x, y) == Board.TREASURE) {
                System.out.println("Schatz gefunden");
                playerBoard.setFieldHit(x, y);
            }

            try {
                if (playerBoard.areAllTreasuresFound()) {

                    printMassege(LOST);
                    running = false;
                    pause();

                }
            } catch (Exception e) {
                System.out.println("DU HAST VERLOREN!!!");
            }

        } while (playerBoard.getField(x, y) != Board.EMPTY);

        if (playerBoard.getField(x, y) == Board.EMPTY) {
            playerBoard.setFieldNO_TREASURE_FOUND(x, y);
            System.out.println("Kein Schatz gefunden");
            System.out.println();
        }
        return shot;
    }

    /**
     * Checks if game is completed
     * 
     * @return
     */
    public boolean isFinished() {
        return playerBoard.areAllTreasuresFound() || villainBoard.areAllTreasuresFound();
    }

    /**
     * Converts alphanumeric board coordinates to array indexes, e.g. A1 to [0,0]
     */
    public static int[] convertCoordinatesToInt(String input) {
        int x = input.toUpperCase().charAt(0) - 65;
        int y = Integer.parseInt(input.substring(1)) - 1;
        return new int[] { x, y };
    }

    /**
     * Converts array indexes to ahlphanumeric board coordinates, e.g. [0,0] to A1
     */
    public static String convertCoordinatesToString(int[] input) {
        char x = (char) (input[0] + 65);
        int i = input[1] + 1;
        String y = Integer.toString(i);
        return x + y;
    }

    /**
     * The methode reads files containing masseges like won, lost or logo for menu.
     * 
     * @param fileName File menu in the directory
     * @throws FileNotFoundException catch when file doesn't exist
     * @throws InterruptedException  catch when interrupted
     */
    public static void printMassege(String fileName) throws FileNotFoundException, InterruptedException {
        File file = new File(fileName);
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (fileName == "lost.txt") {
                    System.out.println("\u001b[31m" + line + "\u001b[0m");
                }
                if (fileName == "logo.txt") {
                    System.out.println("\u001b[36m" + line + "\u001b[0m");
                }
                if (fileName == "won.txt") {
                    System.out.println("\u001b[32m" + line + "\u001b[0m");
                }
                Thread.sleep(25);
            }
        }
    }
}
