package com.heckntarnation.rpgbattleengine.cons.Battle;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.cons.Characters.Player;
import com.heckntarnation.rpgbattleengine.lua.character_lookup;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.luaj.vm2.LuaValue;

/**
 * This class should be complete made by you, the only calls by the engine will
 * be to the tick and setCharacter methods. It will also use the shouldbeRemoved
 * boolean, and the namespace + id strings. Feel free to add any variables and
 * methods in your implementation.
 *
 * @author Ben
 */
public class StatusEffect implements Serializable {

    public static ArrayList<StatusEffect> fromJSON(JSONArray arr, Object character) {
        ArrayList<StatusEffect> effects = new ArrayList<>();
        for (Object o : arr) {
            JSONObject jo = (JSONObject) o;
            StatusEffect ef = (StatusEffect) BattleEngine.jsonHandler.getObject((String) jo.get("id")).object;
            ef.character = character;
            ef.tickScript = (String) jo.get("tickScript");
        }
        return effects;
    }

    public String tickScript;

    /**
     * For JSON loading
     */
    public String namespace, id;

    public Object character;
    public boolean shouldBeRemoved;

    public StatusEffect() {
    }

    public StatusEffect(Object[] props) {

    }

    /**
     * Main status effect code (should be overridden)
     */
    public void tick() {
        BattleEngine.luaHandler.runscript(tickScript, LuaValue.valueOf(character_lookup.lookupID(character)), "character");
    }

    /**
     * Sets the character this is attached to
     *
     * @param character
     */
    public void setCharacter(Player character) {
        this.character = character;
    }

}
