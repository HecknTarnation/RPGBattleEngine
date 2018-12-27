package coolninja.rpg.Console;

/**
 * Built-in console effects functions
 * @version 1.0
 * @since 1.0
 */
public class Console {
    
    /**
     * Creates 3 dots for the amount inputted
     * @param the amount of times
     * @param the time between (in ms)
     * @since 1.0
     */
    public static void Dots(int time, long timeBetween){
        for(int i = 0; i < time; i++){
            try {
                Thread.sleep(timeBetween);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(".");
        }
    }
    
    /**
     * Prints an error message with the given text
     * @since 1.0
     * @param the text to print
     * @param time to wait after printint
     */
    public static void printError(String text, long time){
        System.out.println(Colors.RED_BACKGROUND+text);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Colors.RESET();
    }
    
    /**
     * Clears the console
     * @since 1.0
     */
    public static void clear(){
        System.out.print("\033[H\033[2J");
    }
    
    /**
     * Waits the amount of time given (in ms)
     * @since 1.0
     */
    public static void waitReal(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Waits the amount of half seconds given
     * @since 1.0
     */
    public static void waitHalf(int time){
        try {
            Thread.sleep(500*time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Waits the amount of time given (in seconds)
     * @since 1.0
     */
    public static void waitFull(int time){
        try {
            Thread.sleep(1000*time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
