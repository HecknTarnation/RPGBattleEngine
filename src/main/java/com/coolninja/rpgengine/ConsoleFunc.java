package com.coolninja.rpgengine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class ConsoleFunc {

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsoleFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
    }

}
