import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Al-Hazmi, Mojeeb Khaled Mohammed
 * @author Rahsepar, Mohammad Ferdous
 * @see Requires Java 11 or higher.
 */
public class TreasuryHuntApp {
    // attributes
    private TreasuryHuntGame game;
    private static boolean runningMenu = true;
    private final Path saveFilePath = Path.of("treasuryHunt.save");
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        TreasuryHuntApp treasuryHuntApp = new TreasuryHuntApp();

        TreasuryHuntGame.printMassege("logo.txt");
        System.out.println();
        treasuryHuntApp.mainMenu();

    }
    // operations

    /**
     * Checks if menu is running
     * 
     * @return
     */
    public static boolean getRunningGame() {
        return runningMenu;
    }

    /**
     * Simulates game menu and shows on console
     */
    public void mainMenu() {

        while (runningMenu) {
            System.out.println("(1) Neues Spiel starten");
            if (hasRunningGame()) {
                System.out.println("(2) Spiel fortsetzen");
            }
            if (hasSavedGame()) {
                System.out.println("(3) Spiel laden");
            }
            if (hasRunningGame()) {
                System.out.println("(4) Spiel speichern");
            }
            System.out.println("(5) Beenden");
            System.out.println("Bitte wähle zwischen (1) - (5)");

            try {
                int option = 0;

                option = scanner.nextInt();

                System.out.println("Zahl zwischen 1 und 5 auswählen");
                switch (option) {
                    case 1:
                        System.out.println("Neues Spiel wird gestartet");
                        startNewGame();

                        break;
                    case 2:
                        System.out.println("Spiel wird fortgesetzt");
                        continueGame();
                        break;
                    case 3:
                        System.out.println("Spielstand wird geladen");
                        try {
                            loadGame();
                        } catch (Exception e) {
                            System.out.println("Spiel wurde nicht geladen");
                        }
                        break;
                    case 4:
                        System.out.println("Spielstand wird gespeichert");
                        try {
                            saveGame();
                        } catch (Exception e) {
                            System.out.println("Spiel wurde nicht gespeichert");
                        }
                        break;
                    case 5:
                        System.out.println("Spiel ausgeschaltet");
                        closeGame();
                        break;
                    default:
                        System.out.println("Wähle zwischen 1-5");
                }
            } catch (Exception e) {
                System.out.println("Sie können nur vorhandene Optionen auswählen");
            }
        }
    }

    /**
     * Restores a game from the file "treasuryHunt.save"
     */
    private void loadGame() {
        if (!hasSavedGame()) {
            System.out.println("Kein gespeicherter Spielstand vorhanden.");
            return;
        }

        try {
            String saveGame = Files.readString(saveFilePath, StandardCharsets.UTF_8);
            String[] boards = saveGame.split("\n");
            Board playerBoard = new Board(boards[0]);
            Board villainBoard = new Board(boards[1]);
            this.game = new TreasuryHuntGame(playerBoard, villainBoard);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed");
        }
    }

    /**
     * Saves a game into the file "treasuryHunt.save"
     */
    private void saveGame() {
        File file = saveFilePath.toFile();

        if (file.exists())
            file.delete();
        try {
            file.createNewFile();

            String playerBoard = game.playerBoard.exportAsString();
            String villainBoard = game.villainBoard.exportAsString();
            Files.writeString(file.toPath(), playerBoard + villainBoard, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Save failed");
        }
    }

    /**
     * Checks if file "treasuryHunt.save" exists
     * 
     * @return true/false
     */
    private boolean hasSavedGame() {
        return saveFilePath.toFile().exists();
    }

    /**
     * Checks if game is running
     * 
     * @return boolean true/false
     */
    private boolean hasRunningGame() {
        return !(game == null || game.isFinished());
    }

    /**
     * continue game
     */
    private void continueGame() {
        this.game.run();
    }

    /**
     * Start a new game
     */
    private void startNewGame() {
        this.game = new TreasuryHuntGame();
        continueGame();

    }

    /**
     * Closes the game
     */
    public static void closeGame() {
        TreasuryHuntApp.runningMenu = false;
    }

}
