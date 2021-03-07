package com.coolninja.rpgengine.arrays;

import com.coolninja.rpgengine.enums.StatusArrayPosition;
import java.util.ArrayList;
import org.json.simple.JSONArray;

/**
 *
 * @author Ben
 */
public class StatusArray {

    public ArrayList<Object> statuses = new ArrayList<>();

    public StatusArray() {

    }

    public StatusArray modify(StatusArrayPosition pos, Object value) {
        statuses.set(pos.actualPos, value);
        return this;
    }

    public Object get(StatusArrayPosition index) {
        return statuses.get(index.actualPos);
    }

    public static StatusArray fromJSONArr(JSONArray json) {
        StatusArray arr = new StatusArray();

        arr.modify(StatusArrayPosition.Health, json.get(StatusArrayPosition.Health.actualPos));
        arr.modify(StatusArrayPosition.Mana, json.get(StatusArrayPosition.Mana.actualPos));
        arr.modify(StatusArrayPosition.ATK, json.get(StatusArrayPosition.ATK.actualPos));
        arr.modify(StatusArrayPosition.DEF, json.get(StatusArrayPosition.DEF.actualPos));
        arr.modify(StatusArrayPosition.Luck, json.get(StatusArrayPosition.Luck.actualPos));
        arr.modify(StatusArrayPosition.MATK, json.get(StatusArrayPosition.MATK.actualPos));
        arr.modify(StatusArrayPosition.MDEF, json.get(StatusArrayPosition.MDEF.actualPos));
        arr.modify(StatusArrayPosition.Weakness, json.get(StatusArrayPosition.Weakness.actualPos));
        arr.modify(StatusArrayPosition.AILevel, json.get(StatusArrayPosition.AILevel.actualPos));
        arr.modify(StatusArrayPosition.StatusEffect, json.get(StatusArrayPosition.StatusEffect.actualPos));

        return arr;
    }

}
