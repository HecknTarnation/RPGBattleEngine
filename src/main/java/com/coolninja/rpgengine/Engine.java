package com.coolninja.rpgengine;

import com.coolninja.rpgengine.Cons.Enemy;
import com.coolninja.rpgengine.handlers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The main file for the engine
 *
 * @version 1.0
 * @since 1.0
 * @author Ben
 */
public class Engine {

    public static BattleHandler battleHandler = new BattleHandler();
    public static InputHandler inputHandler = new InputHandler();
    public static LocalizationHandler localizationHandler = new LocalizationHandler();
    public static SoundHandler soundHandler = new SoundHandler();
    public static DeathHandler deathHandler = new DeathHandler();

    private static boolean initialized = false;

    public static void init() {
        localizationHandler.init();
        inputHandler.init();
        initialized = true;
    }

    /**
     * Ends the engine's operation
     */
    public static void end() {
        inputHandler.end();
    }

    /**
     * Used to change handlers to custom ones (leave any null to use default
     * ones)
     *
     * @param bHandler
     * @param iHandler
     * @param lHandler
     * @param sHandler
     */
    public static void init(BattleHandler bHandler, InputHandler iHandler, LocalizationHandler lHandler, SoundHandler sHandler, DeathHandler dHandler) {
        battleHandler = bHandler == null ? battleHandler : bHandler;
        inputHandler = iHandler == null ? inputHandler : iHandler;
        localizationHandler = lHandler == null ? localizationHandler : lHandler;
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

    public static Object loadJSON(File file) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(file.toString()));

        JSONObject json = (JSONObject) obj;

        return loadJSON(json);
    }

    //TODO: Add JSON loading of enemies, characrers, items, status effects, etc.
    public static Object loadJSON(JSONObject json) throws ParseException {
        return null;
    }

    public static void playSound(URI sound, int volume, int repeatTime) {
        soundHandler.playSound(sound, volume, repeatTime);
    }
}
