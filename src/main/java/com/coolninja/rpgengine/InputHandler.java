package com.coolninja.rpgengine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * The built-in input handler
 *
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class InputHandler implements NativeKeyListener {

    public static String[] menu;
    private static int menuIndex;

    //-1 = None, 0 = Menu
    private static int currentMode = -1;

    public static InputHandler instance;

    public static void init() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputHandler i = new InputHandler();
        GlobalScreen.addNativeKeyListener(i);
        instance = i;
    }

    public static void doMenu(String[] m) {
        doMenu(m, "");
    }

    public static void doMenu(String[] m, String printFirst) {
        menu = m;
        boolean run = true;
        while (run) {
            System.out.println(printFirst);
            for (int i = 0; i < menu.length; i++) {
                System.out.println(menuIndex == i ? Vars.Selected_Color : "" + "-" + menu[i]);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            ConsoleFunc.clear();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        if (currentMode == 0) {
            if (nke.getKeyCode() == Vars.Controls[0]) {
                menuIndex++;
            }
            if (nke.getKeyCode() == Vars.Controls[2]) {
                menuIndex--;
            }
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        if (nke.getKeyCode() == Vars.Controls[0]) {
            menuIndex++;
        }
        if (nke.getKeyCode() == Vars.Controls[2]) {
            menuIndex--;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {

    }

}
