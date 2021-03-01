package com.coolninja.rpgengine;

/**
 * Codes used for changing text color
 *
 * @version 1.0
 * @since 1.0
 */
public class Colors {

    private static final String RESET = "\u001B[0m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Prints out a control code that resets the colors
     *
     * @since 1.0
     */
    public static void RESET() {
        System.out.print(RESET);
    }

    /**
     * Returns a control code that resets the colors
     *
     * @return
     * @since 1.0
     */
    public static String reset() {
        return RESET;
    }

}
