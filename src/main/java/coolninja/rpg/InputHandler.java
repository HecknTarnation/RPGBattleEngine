package coolninja.rpg;

import java.util.Scanner;

/**
 * The built-in input handler
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class InputHandler {
    
    private static Scanner scan = new Scanner(System.in);
    
    /**
     * Gets the input of the player as a string
     * @since 1.0
     */
    public static String getInput(){
        return scan.nextLine();
    }
    
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
