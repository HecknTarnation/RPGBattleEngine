package com.heckntarnation.rpgbattleengine.handlers;

import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.heckntarnation.rpgbattleengine.Colors;
import com.heckntarnation.rpgbattleengine.ConsoleFunc;
import com.heckntarnation.rpgbattleengine.Vars;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;
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
    private int currentMode = -1;
    private boolean enterPressed = false;

    public void init() {
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
        menu = temp.toArray(menu);
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
            if (code == Vars.Controls[0]) {
                menuIndex--;
            }
            if (code == Vars.Controls[2]) {
                menuIndex++;
            }
            if (code == Vars.Controls[4]) {
                enterPressed = true;
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {

    }

    private class VoidDispatchService extends AbstractExecutorService {

        private boolean running = false;

        public VoidDispatchService() {
            running = true;
        }

        @Override
        public void shutdown() {
            running = false;
        }

        @Override
        public List<Runnable> shutdownNow() {
            running = false;
            return new ArrayList<>(0);
        }

        @Override
        public boolean isShutdown() {
            return !running;
        }

        @Override
        public boolean isTerminated() {
            return !running;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }

        @Override
        public void execute(Runnable r) {
            r.run();
        }
    }
}
