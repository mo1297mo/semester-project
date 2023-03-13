import java.util.List;
import java.util.Random;

/**
 * @author Al-Hazmi, Mojeeb Khaled Mohammed
 * @author Rahsepar, Mohammad Ferdous
 * @see Holds the state of one players board
 *      as well as the methods to generate a board and process searches.
 */

public class Board {

    // attributes
    public static final char EMPTY = '.';
    public static final char TREASURE = 'O';
    public static final char HIT = 'X';
    public static final char NO_TREASURE_FOUND = '-';

    public static final int BOARD_SIZE = 5;

    private final char[][] fields = new char[BOARD_SIZE][BOARD_SIZE];
    Random random = new Random();

    // constructor
    /**
     * Create a new Board with Treasures
     */
    public Board() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                fields[i][j] = EMPTY;
            }
        }

        // TODO hide treasures (s. Aufgabe 5)
        int option = random.nextInt(3); // -----> Bug
        // to choice a variante
        switch (option) {
            case 0:
                lTreasure();
                break;
            case 1:
                verticalTreasure();
                break;
            case 2:
                horizontalTreasure();
                break;
            default:
                break;
        }

        littleTreasure();

    }

    /**
     * create a L-shape big treasure
     */
    public void lTreasure() {
        int g1;
        int g2;
        boolean L1 = true;

        do {

            g1 = random.nextInt(fields.length);
            g2 = random.nextInt(fields.length);
            if (g1 == 2 || g2 == 2) {
                L1 = false;
            }

            // Spalte = 2
            if (g1 == 2) {
                if (g2 == 0 || g2 == 4) {
                    boolean rand1 = random.nextBoolean();
                    for (int i = 0; i < 3; i++) {
                        fields[g1][g2] = TREASURE;
                        if (rand1) {
                            g1--;
                        } else {
                            g1++;
                        }
                    }
                    if (g1 == -1) {
                        g1++;
                    }
                    if (g1 == 5) {
                        g1--;
                    }
                    if (g2 == 0) {
                        fields[g1][++g2] = TREASURE;
                    }
                    if (g2 == 4) {
                        fields[g1][--g2] = TREASURE;
                    }
                } else {
                    boolean rand2 = random.nextBoolean();
                    for (int i = 0; i < 3; i++) {
                        fields[g1][g2] = TREASURE;
                        if (rand2) {
                            g1--;
                        } else {
                            g1++;
                        }
                    }
                    if (g1 == -1) {
                        g1++;
                    }
                    if (g1 == 5) {
                        g1--;
                    }
                    if (rand2) {
                        fields[g1][++g2] = TREASURE;
                    } else {
                        fields[g1][--g2] = TREASURE;
                    }
                }
            }

            // Zeile = 2
            if (g2 == 2) {
                if (g1 == 0 || g1 == 4) {
                    boolean rand1 = random.nextBoolean();
                    for (int i = 0; i < 3; i++) {
                        fields[g1][g2] = TREASURE;
                        if (rand1) {
                            g2--;
                        } else {
                            g2++;
                        }
                    }
                    if (g2 == -1) {
                        g2++;
                    }
                    if (g2 == 5) {
                        g2--;
                    }
                    if (g1 == 0) {
                        fields[++g1][g2] = TREASURE;
                    }
                    if (g1 == 4) {
                        fields[--g1][g2] = TREASURE;
                    }
                } else {
                    boolean rand2 = random.nextBoolean();
                    for (int i = 0; i < 3; i++) {
                        fields[g1][g2] = TREASURE;
                        if (rand2) {
                            g2--;
                        } else {
                            g2++;
                        }
                    }
                    if (g2 == -1) {
                        g2++;
                    }
                    if (g2 == 5) {
                        g2--;
                    }
                    if (rand2) {
                        fields[++g1][g2] = TREASURE;
                    } else {
                        fields[--g1][g2] = TREASURE;
                    }
                }

            }

        } while (L1);

    }

    /**
     * create a vertical big treasure
     */
    public void verticalTreasure() {
        int g1;
        int g2;
        int L1 = 0;

        do {
            g1 = random.nextInt(fields.length);
            g2 = random.nextInt(fields.length);
            if (g2 == 2) {
                L1 = 2;
            }
            if (g2 == 0 || g2 == 1) {
                for (int i = 0; i < 4; i++) {
                    fields[g1][g2] = TREASURE;
                    g2++;
                }
            }
            if (g2 == 3 || g2 == 4) {
                for (int i = 0; i < 4; i++) {
                    fields[g1][g2] = TREASURE;
                    g2--;
                }
            }

        } while (L1 == 2);
    }

    /**
     * create a horizontal big treasure
     */
    public void horizontalTreasure() {
        int g1;
        int g2;
        int L1 = 0;

        do {
            g1 = random.nextInt(fields.length);
            g2 = random.nextInt(fields.length);
            if (g1 == 2) {
                L1 = 2;
            }
            if (g1 == 0 || g1 == 1) {
                for (int i = 0; i < 4; i++) {
                    fields[g1][g2] = TREASURE;
                    g1++;
                }
            }
            if (g1 == 3 || g1 == 4) {
                for (int i = 0; i < 4; i++) {
                    fields[g1][g2] = TREASURE;
                    g1--;
                }
            }

        } while (L1 == 2);

    }

    /**
     * create 4 little treasure
     */
    public void littleTreasure() {
        int i = 0;
        while (i < 4) {
            int p1 = random.nextInt(BOARD_SIZE);
            int p2 = random.nextInt(BOARD_SIZE);
            if (fields[p1][p2] == EMPTY) {
                fields[p1][p2] = TREASURE;
                i++;
            }
        }

    }

    // Operations
    /**
     * Create a Board from an exported string.
     */
    public Board(String savedBoard) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                int index = y * BOARD_SIZE + x;
                fields[x][y] = savedBoard.charAt(index);
            }
        }
    }

    /**
     * Prints the board to System.out
     *
     * @param hideShips if TRUE, replaces ships by empty fields in output
     */
    public void print(boolean hideShips) {
        /* print column headers A - J */
        System.out.print("# ");
        for (int x = 0; x < fields[0].length; x++) {
            char column = (char) (x + 65);
            System.out.print(" " + column);
        }
        System.out.println();

        for (int y = 0; y < fields.length; y++) {
            /* print row number */
            int rowNumber = y + 1;
            System.out.print(rowNumber + " ");
            if (rowNumber < 10)
                System.out.print(" ");

            /* print row */
            for (int x = 0; x < fields[y].length; x++) {
                char output = fields[x][y];
                if (output == TREASURE && hideShips)
                    output = EMPTY;
                System.out.print(output + " ");
            }
            System.out.println();
        }
    }

    /**
     * This methode exports, what is it in field and changes it to String, so that
     * the board is drawn
     * 
     * @return String
     */
    public String exportAsString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                builder.append(fields[x][y]);
            }
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * This methode checks, if every treasures were found
     * 
     * @return FALSE if at least one ship is remaining. TRUE otherwise.
     */
    public boolean areAllTreasuresFound() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (fields[x][y] == TREASURE)
                    return false;
            }
        }
        return true;
    }

    /**
     * gives the field
     * 
     * @param x The first coordinate row
     * @param y The second coordinate split
     * @return gives, what is it in field
     */
    public char getField(int x, int y) {
        return fields[x][y];
    }

    /**
     * set the field hit
     * 
     * @param x coordinate row
     * @param y coordinate column
     */
    public void setFieldHit(int x, int y) {
        fields[x][y] = HIT;
    }

    /**
     * set the field searched
     * 
     * @param x coordinate row
     * @param y coordinate column
     */
    public void setFieldNO_TREASURE_FOUND(int x, int y) {
        fields[x][y] = NO_TREASURE_FOUND;
    }
}
