package com.heckntarnation.rpgbattleengine;

import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;
import com.heckntarnation.rpgbattleengine.cons.Items.Item;
import com.heckntarnation.rpgbattleengine.handlers.*;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;

/**
 * The main file for the engine
 *
 * @version 1.0
 * @since 1.0
 * @author Ben
 */
public class BattleEngine {

    public static BattleHandler battleHandler = new BattleHandler();
    public static InputHandler inputHandler = new InputHandler();
    public static LocalizationHandler localizationHandler = new LocalizationHandler();
    public static SoundHandler soundHandler = new SoundHandler();
    public static DeathHandler deathHandler = new DeathHandler();

    /**
     * An HashMap that can be used to store items, using an id (it's recommended
     * to use namespace:item_id for this). <br>You must use this if you want to
     * interface with HeckScript.
     */
    public static HashMap<String, Item> itemList = new HashMap<String, Item>();

    private static boolean initialized = false;

    public static void init() {
        //Uninitilizes the engine before reinitilizing.
        if (initialized) {
            end();
        }
        localizationHandler.init();
        inputHandler.init();
        soundHandler.init();
        initialized = true;
    }

    /**
     * Ends the engine's operation
     */
    public static void end() {
        inputHandler.end();
        initialized = false;
        System.gc();
    }

    /**
     * Used to change handlers to custom ones (leave any null to use default
     * one/one's you already set)
     *
     * @param bHandler
     * @param iHandler
     * @param locHandler
     * @param sHandler
     * @param dHandler
     */
    public static void init(BattleHandler bHandler, InputHandler iHandler, LocalizationHandler locHandler, SoundHandler sHandler, DeathHandler dHandler) {
        battleHandler = bHandler == null ? battleHandler : bHandler;
        inputHandler = iHandler == null ? inputHandler : iHandler;
        localizationHandler = locHandler == null ? localizationHandler : locHandler;
        soundHandler = sHandler == null ? soundHandler : sHandler;
        deathHandler = dHandler == null ? deathHandler : dHandler;

        init();
    }

    public static void switchLang(String key) {
        localizationHandler.changLang(key);
    }

    public static void startBattle(boolean canRun, Enemy[] en) {
        battleHandler.startBattle(canRun, en);
    }

    public static void playSound(URI sound, int repeatTime) {
        soundHandler.playSound(sound, repeatTime);
    }

    public static void playSound(File sound, int repeatTime) {
        soundHandler.playSound(sound, repeatTime);
    }

    public static void playSound(InputStream sound, int repeatTime) {
        soundHandler.playSound(sound, repeatTime);
    }

    /**
     * Returns some info about the JVM in an Object[], in the following format.
     * <br>
     * (long) Available Processors, (long) Max Memory, (long) Total Memory,
     * (long) Free Memory.
     *
     * @return
     */
    public static Object[] getJVMInfo() {
        Object[] info = new Object[4];
        info[0] = Runtime.getRuntime().availableProcessors();
        info[1] = Runtime.getRuntime().maxMemory();
        info[2] = Runtime.getRuntime().totalMemory();
        info[3] = Runtime.getRuntime().freeMemory();
        return info;
    }

    /**
     * Returns some info about the Engine in an Object[], in the following
     * format.
     * <br>
     * (String) Version, (boolean) initialized.
     *
     * @return
     */
    public static Object[] getEngineInfo() {
        Object[] info = new Object[2];
        info[0] = "0.0.0-Beta";
        info[1] = initialized;
        return info;
    }
}
