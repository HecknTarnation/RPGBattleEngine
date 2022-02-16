package com.heckntarnation.rpgbattleengine.cons.Battle;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Ben
 */
public class Weakness implements Serializable {

    public static boolean isWeak(ArrayList<Weakness> weakness, Move move) {
        if (move.type == null || weakness == null) {
            return false;
        }
        return weakness.stream().anyMatch(w -> (w.type.equalsIgnoreCase(move.type.type)));
    }

    public static ArrayList<Weakness> fromJSONforCharacter(JSONObject file) {
        JSONObject[] objs = (JSONObject[]) file.get("weakness");
        ArrayList<Weakness> weakness = new ArrayList<>();
        for (Object o : objs) {
            Weakness w = new Weakness((String) file.get("type"), (float) file.get("effectiveness"));
            w.id = (String) file.get("id");
            w.namespace = (String) file.get("namespace");
            weakness.add(w);
        }
        return weakness;
    }

    /**
     * TODO: this
     *
     * @param arr
     * @return
     */
    public static ArrayList<Weakness> fromJSON(JSONArray arr) {
        return null;
    }

    /**
     * For JSON loading
     */
    public String namespace, id;

    //ex: "fire", "water"
    public String type;
    public float effectiveness;

    /**
     * Creates a new weakness, this is used for this formula: <br>
     * if damage.type = weakness.type, <br>
     * then damage * effectiveness = final damage
     *
     * <br>
     * If effectiveness {@literal <} 0 it will be treated as a resistence.
     *
     * @param type
     * @param effectiveness
     */
    public Weakness(String type, float effectiveness) {
        this.type = type;
        this.effectiveness = effectiveness;
    }

    public Weakness setType(String type) {
        this.type = type;
        return this;
    }

    public Weakness setEffectiveness(float eff) {
        this.effectiveness = eff;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public float getEffectiveness() {
        return this.effectiveness;
    }

}
