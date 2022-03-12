package com.heckntarnation.rpgbattleengine.cons.Characters;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import com.heckntarnation.rpgbattleengine.cons.Battle.Move;
import com.heckntarnation.rpgbattleengine.cons.Battle.StatusEffect;
import com.heckntarnation.rpgbattleengine.cons.Battle.Weakness;
import com.heckntarnation.rpgbattleengine.cons.Items.Drop;
import com.heckntarnation.rpgbattleengine.enums.AILevel;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ben
 */
public class Enemy implements Serializable, Cloneable {

    /**
     * For JSON loading
     */
    public String namespace, id;

    public String name;
    public int health, maxHealth, attack, defense, luck, mAttack, mDefense;
    /**
     * Chance of dodging an attack
     */
    public float evasion = 0.0f;
    /**
     * The damage to add on crit (percentage).
     */
    public float critDMG = 0.5f;
    public int expVal;
    public Move[] moves;
    public AILevel aiLevel;
    public ArrayList<Weakness> weaknesses = new ArrayList<>();
    public ArrayList<StatusEffect> statusEffects = new ArrayList<>();
    public Drop[] drops;

    public Enemy(String name, int health, int expVal) {
        this.name = name;
        String locName = BattleEngine.localizationHandler.getLocalizedString_surpressed(name);
        if (locName != null) {
            this.name = locName;
        }
        this.health = health;
        this.maxHealth = health;
        this.expVal = expVal;
    }

    public Enemy(String name, int expVal) {
        this.name = name;
        String locName = BattleEngine.localizationHandler.getLocalizedString_surpressed(name);
        if (locName != null) {
            this.name = locName;
        }
        this.expVal = expVal;
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
            this.weaknesses = (ArrayList<Weakness>) stats.get(StatusArrayPosition.Weakness);
        }
        if (stats.get(StatusArrayPosition.AILevel) != null) {
            this.aiLevel = (AILevel) stats.get(StatusArrayPosition.AILevel);
        }
        if (stats.get(StatusArrayPosition.StatusEffect) != null) {
            this.statusEffects = (ArrayList<StatusEffect>) stats.get(StatusArrayPosition.StatusEffect);
        }
        if (stats.get(StatusArrayPosition.Evasion) != null) {
            this.evasion = (float) stats.get(StatusArrayPosition.Evasion);
        }
        if (stats.get(StatusArrayPosition.CRIT_DMG) != null) {
            this.critDMG = (float) stats.get(StatusArrayPosition.CRIT_DMG);
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

    public void statusEffectTick() {
        if (statusEffects == null || statusEffects.isEmpty()) {
            return;
        }
        statusEffects.forEach(effect -> {
            effect.tick();
            statusEffects.removeIf(filter -> {
                return effect.shouldBeRemoved;
            });
        });
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

}
