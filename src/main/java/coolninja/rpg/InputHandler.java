package coolninja.rpg;

import java.io.IOException;
import java.util.Scanner;

/**
 * The built-in input handler
 *
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class InputHandler {

    private static final Scanner scan = new Scanner(System.in);

    /**
     * Gets the input of the player as a string
     *
     * @since 1.0
     * @return string
     */
    public static String getInput() {
        System.out.print("Input: ");
        return scan.nextLine();
    }

    /**
     * Waits for the user to press enter
     *
     * @since 1.0
     */
    public static void pressEnter() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }

    /**
     * Used for Yes or No questions. Returns true for yes.
     *
     * @return boolean
     * @since 1.0
     */
    public static boolean getYON() {
        System.out.print("(Y) or (N):");
        return scan.nextLine().equalsIgnoreCase("Y");
    }

    /**
     * Closes the scanner object, only do this when you are done with it.
     *
     * @since 1.0
     */
    public static void closeScanner() {
        scan.close();
    }

}
