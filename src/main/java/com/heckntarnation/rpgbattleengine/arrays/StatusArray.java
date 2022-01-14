package com.heckntarnation.rpgbattleengine.arrays;

import com.heckntarnation.rpgbattleengine.cons.Battle.StatusEffect;
import com.heckntarnation.rpgbattleengine.cons.Battle.Weakness;
import com.heckntarnation.rpgbattleengine.enums.AILevel;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.simple.JSONArray;
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

    public StatusArray put(StatusArrayPosition pos, Object value) {
        this.statuses.set(pos.actualPos, value);
        return this;
    }

    public Object get(StatusArrayPosition index) {
        return this.statuses.get(index.actualPos);
    }

    public static StatusArray fromJSONObj(JSONObject json, Object character) {
        StatusArray arr = new StatusArray();

        arr.put(StatusArrayPosition.Health, json.get("health"));
        arr.put(StatusArrayPosition.MaxHealth, json.get("maxHealth"));
        arr.put(StatusArrayPosition.MaxMana, json.get("maxMana"));
        arr.put(StatusArrayPosition.Mana, json.get("mana"));
        arr.put(StatusArrayPosition.ATK, json.get("attack"));
        arr.put(StatusArrayPosition.DEF, json.get("defense"));
        arr.put(StatusArrayPosition.Luck, json.get("luck"));
        arr.put(StatusArrayPosition.MATK, json.get("magicAttack"));
        arr.put(StatusArrayPosition.MDEF, json.get("magicDefense"));
        arr.put(StatusArrayPosition.Weakness, Weakness.fromJSON(json.get("weakness")));
        switch ((String) json.get("aiLevel")) {
            case "random": {
                arr.put(StatusArrayPosition.AILevel, AILevel.Random);
                break;
            }
            case "damage": {
                arr.put(StatusArrayPosition.AILevel, AILevel.Damage);
                break;
            }
            case "damageMagic": {
                arr.put(StatusArrayPosition.AILevel, AILevel.DamageMagic);
                break;
            }
            case "weakness": {
                arr.put(StatusArrayPosition.AILevel, AILevel.Weakness);
                break;
            }
            case "weaknessDamage": {
                arr.put(StatusArrayPosition.AILevel, AILevel.WeaknessDamage);
                break;
            }
            case "weaknessDamageMagic": {
                arr.put(StatusArrayPosition.AILevel, AILevel.WeaknessDamageMagic);
                break;
            }
        }
        arr.put(StatusArrayPosition.StatusEffect, StatusEffect.fromJSON((JSONArray) json.get("statusEffects"), character).toArray());
        arr.put(StatusArrayPosition.Evasion, json.get("evasion"));

        return arr;
    }

}
