package com.coolninja.rpgengine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class ConsoleFunc {

    /**
     * Waits the given amount of time, in milliseconds.
     *
     * @param ms
     */
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsoleFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //TODO: this function fails to clear the scrollback of cmd or ps (possibly also mac and linux terminals?)
    /**
     * Clears the screen
     */
    public static void clear() {
        moveHome();
        System.out.print("\033[2J");
    }

    /**
     * Moves the cursor to the "home" position, a.k.a the start of the visible
     * window.
     */
    public static void moveHome() {
        System.out.print("\033[H");
    }

    /**
     * Moves the cursor left
     *
     * @param times
     * @return
     */
    public static String moveLeft(int times) {
        return "\033[" + times + "D";
    }

    /**
     * Moves the cursor right
     *
     * @param times
     * @return
     */
    public static String moveRight(int times) {
        return "\033[" + times + "C";
    }

    /**
     * Prints out str with the amount of dots.
     *
     * @param str
     * @param dotCount
     * @param timeBetween
     */
    public static void dots(String str, int dotCount, long timeBetween) {
        System.out.print(str);
        for (int i = 0; i < dotCount; i++) {
            System.out.print(".");
            try {
                Thread.sleep(timeBetween);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConsoleFunc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.print("\n");
    }

}
