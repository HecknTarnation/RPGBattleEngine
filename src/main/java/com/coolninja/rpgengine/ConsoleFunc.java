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
