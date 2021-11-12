package com.heckntarnation.rpgbattleengine.arrays;

import com.heckntarnation.rpgbattleengine.Cons.StatusEffect;
import com.heckntarnation.rpgbattleengine.Cons.Weakness;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 *
 * @author Ben
 */
public class StatusArray implements Serializable {

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

    public static StatusArray fromJSONObj(JSONObject json) {
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
        //TODO: change to use Weakness.fromJSON() method.
        arr.modify(StatusArrayPosition.Weakness, (Weakness[]) json.get("weakness"));
        arr.modify(StatusArrayPosition.AILevel, json.get("aiLevel"));
        //TODO: change to use StatusEffect.fromJSON() method.
        arr.modify(StatusArrayPosition.StatusEffect, (StatusEffect[]) json.get("statusEffect"));
        arr.modify(StatusArrayPosition.Evasion, json.get("evasion"));

        return arr;
    }

}
