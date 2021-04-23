package com.coolninja.rpgengine.arrays;

import com.coolninja.rpgengine.enums.StatusArrayPosition;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 *
 * @author Ben
 */
public class StatusArray {

    public ArrayList<Object> statuses;

    public StatusArray() {
        this.statuses = new ArrayList<>(10);
        for (int i = 0; i < 20; i++) {
            statuses.add(null);
        }
    }

    public StatusArray modify(StatusArrayPosition pos, Object value) {
        this.statuses.set(pos.actualPos, value);
        return this;
    }

    public Object get(StatusArrayPosition index) {
        return this.statuses.get(index.actualPos);
    }

    public static StatusArray fromJSONArr(JSONObject json) {
        StatusArray arr = new StatusArray();

        arr.modify(StatusArrayPosition.Health, json.get("health"));
        arr.modify(StatusArrayPosition.MaxHealth, json.get("maxHealth"));
        arr.modify(StatusArrayPosition.MaxMana, json.get("maxMana"));
        arr.modify(StatusArrayPosition.Mana, json.get("mana"));
        arr.modify(StatusArrayPosition.ATK, json.get("attack"));
        arr.modify(StatusArrayPosition.DEF, json.get("defense"));
        arr.modify(StatusArrayPosition.Luck, json.get("luck"));
        arr.modify(StatusArrayPosition.MATK, json.get("magicAttack"));
        arr.modify(StatusArrayPosition.MDEF, json.get("magicDefense"));
        arr.modify(StatusArrayPosition.Weakness, json.get("weakness"));
        arr.modify(StatusArrayPosition.AILevel, json.get("aiLevel"));
        arr.modify(StatusArrayPosition.StatusEffect, json.get("statusEffect"));

        return arr;
    }

}
