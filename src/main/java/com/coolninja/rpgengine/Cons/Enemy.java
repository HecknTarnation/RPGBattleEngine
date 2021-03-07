package com.coolninja.rpgengine.Cons;

import com.coolninja.rpgengine.arrays.StatusArray;
import com.coolninja.rpgengine.enums.AILevel;
import com.coolninja.rpgengine.enums.StatusArrayPosition;
import java.io.Serializable;
import org.json.simple.JSONObject;

/**
 *
 * @author Ben
 */
public class Enemy implements Serializable {

    public String name;
    public int health, attack, defense, luck, mAttack, mDefense;
    public int expVal;
    public Move[] moves;
    public AILevel aiLevel;
    public Weakness weakness;
    public StatusEffect statusEffect;
    public Item drop;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public Enemy setStats(StatusArray stats) {
        if (stats.get(StatusArrayPosition.Health) != null) {
            this.health = (int) stats.get(StatusArrayPosition.Health);
        }
        if (stats.get(StatusArrayPosition.ATK) != null) {
            this.attack = (int) stats.get(StatusArrayPosition.ATK);
        }
        if (stats.get(StatusArrayPosition.DEF) != null) {
            this.defense = (int) stats.get(StatusArrayPosition.DEF);
        }
        if (stats.get(StatusArrayPosition.Luck) != null) {
            this.luck = (int) stats.get(StatusArrayPosition.Luck);
        }
        if (stats.get(StatusArrayPosition.MATK) != null) {
            this.mAttack = (int) stats.get(StatusArrayPosition.MATK);
        }
        if (stats.get(StatusArrayPosition.MDEF) != null) {
            this.mDefense = (int) stats.get(StatusArrayPosition.MDEF);
        }
        if (stats.get(StatusArrayPosition.Weakness) != null) {
            this.weakness = (Weakness) stats.get(StatusArrayPosition.Weakness);
        }
        if (stats.get(StatusArrayPosition.AILevel) != null) {
            this.aiLevel = (AILevel) stats.get(StatusArrayPosition.AILevel);
        }
        if (stats.get(StatusArrayPosition.StatusEffect) != null) {
            this.statusEffect = (StatusEffect) stats.get(StatusArrayPosition.StatusEffect);
        }
        return this;
    }

    public Enemy setDrop(Item d) {
        this.drop = d;
        return this;
    }

    public Item getDrop() {
        return this.drop;
    }

    /**
     * TODO: write
     *
     * @return JSONObject
     */
    public JSONObject writeToFile() {

        return null;
    }

}
