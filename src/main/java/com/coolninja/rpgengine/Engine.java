package com.coolninja.rpgengine;

import com.coolninja.rpgengine.Cons.Enemy;
import com.coolninja.rpgengine.Cons.Item;
import com.coolninja.rpgengine.arrays.StatusArray;
import com.coolninja.rpgengine.handlers.BattleHandler;
import com.coolninja.rpgengine.handlers.InputHandler;
import com.coolninja.rpgengine.handlers.LocalizationHandler;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
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

    public static void init() {

    }

    /**
     * Used to change handlers to custom ones (leave any null to use default
     * ones)
     *
     * @param bHandler
     * @param iHandler
     * @param lHandler
     */
    public static void init(BattleHandler bHandler, InputHandler iHandler, LocalizationHandler lHandler) {
        battleHandler = bHandler == null ? battleHandler : bHandler;
        inputHandler = iHandler == null ? inputHandler : iHandler;
        localizationHandler = lHandler == null ? localizationHandler : lHandler;

        init();
    }

    public static void startBattle(Enemy[] en) {
        battleHandler.startBattle(en);
    }

    public static Object loadJSON(URI path) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(path.toString()));

        JSONObject json = (JSONObject) obj;

        return loadJSON(json);
    }

    public static Object loadJSON(JSONObject json) throws ParseException {

        String type = (String) json.get("type");

        if (type.equalsIgnoreCase("Enemy")) {
            JSONArray stats = (JSONArray) json.get("stats");
            Enemy en = new Enemy((String) json.get("name"), (Integer) stats.get(0));
            en.setStats(StatusArray.fromJSONArr(stats));
            en.setDrop((Item) json.get("drop"));
            //TODO: moves
            return en;
        }

        return null;
    }
}
