package com.coolninja.rpgengine;

import com.coolninja.rpgengine.Cons.Enemy;
import com.coolninja.rpgengine.Cons.Item;
import com.coolninja.rpgengine.Cons.Move;
import com.coolninja.rpgengine.Cons.Player;
import com.coolninja.rpgengine.arrays.StatusArray;
import com.coolninja.rpgengine.handlers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
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

    public static void init() {
        localizationHandler.init();
        inputHandler.init();
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
    public static void init(BattleHandler bHandler, InputHandler iHandler, LocalizationHandler lHandler, SoundHandler sHandler) {
        battleHandler = bHandler == null ? battleHandler : bHandler;
        inputHandler = iHandler == null ? inputHandler : iHandler;
        localizationHandler = lHandler == null ? localizationHandler : lHandler;
        soundHandler = sHandler == null ? soundHandler : sHandler;

        init();
    }

    public static void switchLang(String key) {
        localizationHandler.changLang(key);
    }

    public static void startBattle(Enemy[] en) {
        battleHandler.startBattle(en);
    }

    public static Object loadJSON(File file) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(file.toString()));

        JSONObject json = (JSONObject) obj;

        return loadJSON(json);
    }

    //TODO: test this code
    public static Object loadJSON(JSONObject json) throws ParseException {

        String type = (String) json.get("type");

        if (type.equalsIgnoreCase("Enemy")) {
            JSONObject stats = (JSONObject) json.get("stats");
            Enemy en = new Enemy((String) json.get("name"), (int) (long) stats.get("health"));
            en.expVal = (int) ((long) json.get("expVal"));
            en.setStats(StatusArray.fromJSONArr(stats));
            en.setDrop((Item) json.get("drop"));
            en.setMoves(Move.fromJSONArr(((JSONArray) json.get("moves"))));
            return en;
        } else if (type.equalsIgnoreCase("player")) {
            Player plr = new Player((String) json.get("name"));
            StatusArray arr = StatusArray.fromJSONArr((JSONObject) json.get("stats"));
            JSONArray growthRates = (JSONArray) json.get("growthRates");
            JSONArray moves = (JSONArray) json.get("moves");
            JSONArray inv = (JSONArray) json.get("inv");

            plr.load(arr, (int) (long) json.get("level"), growthRates, moves, inv, (int) (long) json.get("exp"), (int) (long) json.get("expToNextLevel"));
            return plr;
        }

        return null;
    }
}
