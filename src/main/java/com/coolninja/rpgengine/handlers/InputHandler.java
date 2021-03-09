package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.Vars;
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

    public String[] menu;
    private int menuIndex;

    //-1 = None, 0 = Menu
    private int currentMode = -1;
    private boolean enterPressed = false;

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

    public int doMenu(String[] m) {
        return doMenu(m, "");
    }

    public int doMenu(String[] m, String printFirst) {
        currentMode = 0;
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
            run = !enterPressed;
        }
        enterPressed = false;
        return menuIndex;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        if (currentMode == 0) {
            if (nke.getKeyCode() == Vars.Controls[0]) {
                menuIndex++;
            }
            if (nke.getKeyCode() == Vars.Controls[2]) {
                menuIndex--;
            }
            if (nke.getKeyCode() == Vars.Controls[4]) {
                enterPressed = true;
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {

    }

}
