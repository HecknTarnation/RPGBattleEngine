package com.coolninja.rpgengine.Cons;

import com.coolninja.rpgengine.arrays.StatusArray;
import com.coolninja.rpgengine.enums.AILevel;
import com.coolninja.rpgengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Ben
 */
public class Enemy implements Serializable, Cloneable {

    public String name;
    public int health, maxHealth, attack, defense, luck, mAttack, mDefense;
    public int expVal;
    public Move[] moves;
    public AILevel aiLevel;
    public Weakness weakness;
    public StatusEffect statusEffect;
    public Drop[] drops;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
    }

    public Enemy setStats(StatusArray stats) {
        if (stats.get(StatusArrayPosition.Health) != null) {
            this.health = (int) stats.get(StatusArrayPosition.Health);
        }
        if (stats.get(StatusArrayPosition.MaxHealth) != null) {
            this.health = (int) stats.get(StatusArrayPosition.MaxHealth);
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

    public Enemy setMoves(Move[] moves) {
        this.moves = moves;
        return this;
    }

    public Enemy setDrop(Drop[] drops) {
        this.drops = drops;
        return this;
    }

    public Drop getDrop() {
        if (drops == null) {
            return null;
        }
        for (Drop d : drops) {
            if (d.getIfShouldDrop()) {
                return d;
            }
        }
        return null;
    }

    /**
     * Creates a clone of this enemy
     *
     * @return
     */
    @Override
    public Enemy clone() {
        try {
            return (Enemy) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Is run when the enemy dies (at the end of everyone's turn when checkWon
     * is run).
     */
    public void onDeath() {

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
