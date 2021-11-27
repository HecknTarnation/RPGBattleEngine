package com.heckntarnation.rpgbattleengine.Cons;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.MathFunc;
import java.io.Serializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Ben
 */
public class Drop implements Serializable {

    public static Drop[] fromJSON(JSONArray objs) {
        Drop[] drops = new Drop[objs.size()];
        for (int i = 0; i < objs.size(); i++) {
            Drop drop;
            if (objs.get(i) instanceof String) {
                drop = (Drop) BattleEngine.jsonHandler.getObject((String) objs.get(i));
                if (drop == null) {
                    drop = (Drop) BattleEngine.jsonHandler.LOAD_LATER;
                    drops[i] = drop;
                    return drops;
                }
            } else {
                JSONObject o = (JSONObject) objs.get(i);
                drop = new Drop((Item) BattleEngine.jsonHandler.getObject((String) o.get("id")), (String) o.get("chance"));
            }
            drops[i] = drop;
        }
        return drops;
    }

    /**
     * For JSON loading
     */
    public String namespace, id;

    public Item item;
    /**
     * The chance to get this item. Ex: 1/128
     */
    public String chance;

    public Drop(Item item, String chance) {
        this.item = item;
        this.chance = chance;
    }

    public Drop setItem(Item i) {
        this.item = i;
        return this;
    }

    public Item getItem() {
        return this.item;
    }

    public Drop setChance(String chance) {
        this.chance = chance;
        return this;
    }

    public Drop setChance(int numerator, int denominator) {
        this.chance = numerator + "/" + denominator;
        return this;
    }

    public String getChance() {
        return this.chance;
    }

    public boolean getIfShouldDrop() {
        String[] c = this.chance.split("(\\/)\\w");
        int[] amount = new int[]{Integer.parseInt(c[1]), Integer.parseInt(c[1]) - Integer.parseInt(c[0])};
        String[] elements = new String[]{"get", "miss"};
        return ((String) MathFunc.hatpull(amount, elements)).equalsIgnoreCase("get");
    }

}
