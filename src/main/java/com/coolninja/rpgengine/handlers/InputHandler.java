package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Colors;
import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.Vars;
import java.io.IOException;
import java.util.Scanner;
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
    public Scanner scan;

    //-1 = None, 0 = Menu, 1 = Text Input
    private int currentMode = -1;
    private boolean enterPressed = false;

    //TODO: fix problem where, upon pressing enter and if the program exits, it will attempt to run the input as a command. This problem also affects the use of the doText and waitUntilEnter method.
    //Problem only occurs when CMD or PowerShell is in focus, if it's not in focus it works as intented.
    public void init() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        GlobalScreen.addNativeKeyListener(this);
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        scan = new Scanner(System.in);
    }

    public int doMenu(String[] m, boolean clearAtEnd) {
        return doMenu(m, "", clearAtEnd);
    }

    public int doMenu(String[] m, String printFirst, boolean clearAtEnd) {
        currentMode = 0;
        menuIndex = 0;
        menu = m;
        boolean run = true;
        while (run) {
            if (menuIndex > m.length - 1) {
                menuIndex = 0;
            }
            if (menuIndex < 0) {
                menuIndex = m.length - 1;
            }
            System.out.println(printFirst);
            for (int i = 0; i < menu.length; i++) {
                System.out.println((menuIndex == i ? Vars.Selected_Color : "") + "  -" + menu[i] + Colors.reset());
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            run = !enterPressed;
            if (!run && !clearAtEnd) {
                break;
            }
            ConsoleFunc.clear();
        }
        enterPressed = false;
        currentMode = -1;
        return menuIndex;
    }

    public String doText() {
        currentMode = 1;
        System.out.print("?: ");
        String s = scan.nextLine();
        currentMode = -1;
        return s;
    }

    public void waitUntilEnter() {
        System.out.println("Press Enter...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ends the InputHandler's operation (you must call init to use InputHandler
     * again)
     */
    public void end() {
        GlobalScreen.removeNativeKeyListener(this);
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        scan.close();
        scan = null;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        if (currentMode == 0) {
            if (nke.getKeyCode() == Vars.Controls[0]) {
                menuIndex--;
            }
            if (nke.getKeyCode() == Vars.Controls[2]) {
                menuIndex++;
            }
            if (nke.getKeyCode() == Vars.Controls[4]) {
                enterPressed = true;
            }
        }
    }

}
