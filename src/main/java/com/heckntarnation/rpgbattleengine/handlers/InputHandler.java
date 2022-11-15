package com.heckntarnation.rpgbattleengine.handlers;

import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.dispatcher.VoidDispatchService;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.Colors;
import com.heckntarnation.rpgbattleengine.ConsoleFunc;
import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.Vars.ControlMapping;
import com.heckntarnation.rpgbattleengine.cons.CycleValues.CycleValue;
import com.heckntarnation.rpgbattleengine.enums.LangKeys;
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
    private CycleValue menuIndex;
    public Scanner scan;

    //-1 = None, 0 = Menu, 1 = Text Input, 2 = wait for enter
    private byte currentMode = -1;
    private boolean enterPressed = false;

    public void init() {
        //TODO: This prevents any keyboard input (system wide) from working if an error occurs while mode == 0. Closing the application fixes this.
        //Input is also disabled system wide while waiting for input (even while command prompt isn't in focus).
        //Not even sure if this can be fixed.
        GlobalScreen.setEventDispatcher(new VoidDispatchService());
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

    public int doMenu(String[] m, boolean clearAtEnd, int startIndex) {
        return doMenu(m, "", clearAtEnd, startIndex);
    }

    public int doMenu(String[] m, String printFirst, boolean clearAtEnd, int startIndex) {
        currentMode = 0;
        menu = null;
        ArrayList<String> temp = new ArrayList<>();
        for (String s : m) {
            if (s != null) {
                temp.add(s);
            }
        }
        menu = temp.toArray(new String[temp.size()]);
        boolean run = true;
        menuIndex = new CycleValue(startIndex, 0, menu.length - 1);
        while (run) {
            System.out.println(printFirst);
            for (int i = 0; i < menu.length; i++) {
                System.out.println((menuIndex.getValue() == i ? Vars.Selected_Color : "") + "  -" + menu[i] + Colors.reset());
            }
            ConsoleFunc.wait(Vars.menuWaitTime);

            run = !enterPressed;
            if (!run && !clearAtEnd) {
                break;
            }
            ConsoleFunc.clear();
        }
        enterPressed = false;
        currentMode = -1;
        return menuIndex.getValue();
    }

    public String doText() {
        currentMode = 1;
        System.out.print("?: ");
        String s = scan.nextLine();
        currentMode = -1;
        return s;
    }

    public void waitUntilEnter() {
        System.out.println(BattleEngine.localizationHandler.getLocalizedString(LangKeys.press_submit));
        currentMode = 2;

        while (!enterPressed) {
            ConsoleFunc.wait(300);
        }
        enterPressed = false;
        currentMode = -1;
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
        //scan.close();
        scan = null;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        //menu
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
                menuIndex.decValue();
            }
            if (code == Vars.Controls.get(ControlMapping.Down)) {
                menuIndex.incValue();
            }
            if (code == Vars.Controls.get(ControlMapping.Submit)) {
                enterPressed = true;
            }
        }
        //wait for enter
        if (currentMode == 2) {
            int code = nke.getKeyCode();
            if (code == Vars.Controls.get(ControlMapping.Submit)) {
                enterPressed = true;
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {

    }

}
