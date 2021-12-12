package com.heckntarnation.rpgbattleengine.handlers;

import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.dispatcher.VoidDispatchService;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.heckntarnation.rpgbattleengine.Colors;
import com.heckntarnation.rpgbattleengine.ConsoleFunc;
import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.Vars.ControlMapping;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private byte currentMode = -1;
    private boolean enterPressed = false;

    public void init() {
        //TODO: This prevents any keyboard input (system wide) from working if an error occurs while mode == 0. Closing the application fixes this.
        //Input is also disabled system wide while in mode 0 (even while command prompt isn't in focus).
        GlobalScreen.setEventDispatcher(new VDPService());
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
        ArrayList<String> temp = new ArrayList<>();
        for (String s : menu) {
            if (s != null) {
                temp.add(s);
            }
        }
        menu = temp.toArray(new String[temp.size()]);
        boolean run = true;
        while (run) {
            if (menuIndex > menu.length - 1) {
                menuIndex = 0;
            }
            if (menuIndex < 0) {
                menuIndex = menu.length - 1;
            }
            System.out.println(printFirst);
            for (int i = 0; i < menu.length; i++) {
                System.out.println((menuIndex == i ? Vars.Selected_Color : "") + "  -" + menu[i] + Colors.reset());
            }
            ConsoleFunc.wait(300);

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
        if (currentMode == 0) {
            try {
                Field f = NativeInputEvent.class.getDeclaredField("reserved");
                f.setAccessible(true);
                f.setShort(nke, (short) 0x01);

            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                System.out.print("[ !! ]\n");
                ex.printStackTrace();
            }
            int code = nke.getKeyCode();
            if (code == Vars.Controls.get(ControlMapping.Up)) {
                menuIndex--;
            }
            if (code == Vars.Controls.get(ControlMapping.Down)) {
                menuIndex++;
            }
            if (code == Vars.Controls.get(ControlMapping.Select)) {
                enterPressed = true;
            }
        }
        if (currentMode == 2) {

        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {

    }

}

class VDPService extends VoidDispatchService {

}
