package coolninja.rpg;

import java.util.Scanner;

/**
 * The built-in input handler
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class InputHandler {
    
    private static final Scanner scan = new Scanner(System.in);
    
    /**
     * Gets the input of the player as a string
     * @since 1.0
     * @return string
     */
    public static String getInput(){
        return scan.nextLine();
    }
    
    /**
     * Waits for the user to press enter
     * @since 1.0
     */
    public static void pressEnter(){
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}
    }
    
}
