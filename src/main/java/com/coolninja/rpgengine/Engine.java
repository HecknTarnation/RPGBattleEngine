package com.coolninja.rpgengine;

import com.coolninja.rpgengine.Battle.BattleHandler;
import com.coolninja.rpgengine.Cons.Enemy;
import com.coolninja.rpgengine.Cons.Item;
import com.coolninja.rpgengine.arrays.StatusArray;
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

    public static BattleHandler battleHandler;
    public static InputHandler inputHandler;

    public static void init() {

    }

    /**
     * Used to change handlers to custom ones (leave any null to use default
     * ones)
     *
     * @param bHandler
     * @param iHandler
     */
    public static void init(BattleHandler bHandler, InputHandler iHandler) {
        battleHandler = bHandler == null ? new BattleHandler() : bHandler;
        inputHandler = iHandler == null ? new InputHandler() : iHandler;

        init();
    }

    public static void startBattle(Enemy[] en) {
        battleHandler.startBattle(en);
    }

    public static Object loadJSON(URI path) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(path.toString()));

        JSONObject json = (JSONObject) obj;

        String type = (String) json.get("type");

        if (type.equalsIgnoreCase("Enemy")) {
            JSONArray stats = (JSONArray) json.get("stats");
            Enemy en = new Enemy((String) json.get("name"), (Integer) stats.get(0));
            en.setStats(StatusArray.fromJSONArr(stats));
            en.setDrop((Item) json.get("drop"));
            return en;
        }

        return null;
    }
}
