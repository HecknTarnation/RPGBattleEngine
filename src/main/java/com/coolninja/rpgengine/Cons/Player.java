package com.coolninja.rpgengine.Cons;

import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.MathFunc;
import com.coolninja.rpgengine.arrays.StatusArray;
import com.coolninja.rpgengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Player implements Serializable {

    public String name;
    public int level, health, maxHealth, mana, maxMana, attack, defense, luck, mAttack, mDefense, exp, expToNextLevel;
    /**
     * {healthgrowthRate, manaGrowhRate, attackGrowthRate, defenseGrowthRate,
     * luckGrowthRate, mAttackGrowthRate, mDefenseGrowthRate};
     */
    public double[] growthRates = {1.0, 1.0, 1.0, 1.0, 0.1, 1.0, 1.0};

    /**
     * Player's moves
     */
    public ArrayList<Move> moves;

    /**
     * 0 = feet 1 = legs 2 = arms 3 = chest 4 = head 5 = weapon 6 = mod
     */
    public Equipment[] equipment = new Equipment[7];

    /**
     * Player inventory
     */
    public ArrayList<Item> inv;

    public Weakness weakness;
    public StatusEffect statusEffect;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Is used for loading saved data.
     *
     * @param arr
     * @param maxHealth
     * @param maxMana
     * @param level
     * @param growthRates
     * @param moves
     * @param inv
     * @param exp
     * @param expNextLevel
     */
    public void load(StatusArray arr, int maxHealth, int maxMana, int level, double[] growthRates, ArrayList<Move> moves, ArrayList<Item> inv, int exp, int expNextLevel) {
        this.setStats(arr);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.growthRates = growthRates;
        this.moves = moves;
        this.inv = inv;
        this.exp = exp;
        this.expToNextLevel = expNextLevel;
    }

    public void setStats(StatusArray arr) {
        if (arr.get(StatusArrayPosition.Health) != null) {
            health = (int) arr.get(StatusArrayPosition.Health);
        }
        if (arr.get(StatusArrayPosition.Mana) != null) {
            mana = (int) arr.get(StatusArrayPosition.Mana);
        }
        if (arr.get(StatusArrayPosition.ATK) != null) {
            attack = (int) arr.get(StatusArrayPosition.ATK);
        }
        if (arr.get(StatusArrayPosition.DEF) != null) {
            defense = (int) arr.get(StatusArrayPosition.DEF);
        }
        if (arr.get(StatusArrayPosition.Luck) != null) {
            luck = (int) arr.get(StatusArrayPosition.Luck);
        }
        if (arr.get(StatusArrayPosition.MATK) != null) {
            mAttack = (int) arr.get(StatusArrayPosition.MATK);
        }
        if (arr.get(StatusArrayPosition.MDEF) != null) {
            mDefense = (int) arr.get(StatusArrayPosition.MDEF);
        }
        if (arr.get(StatusArrayPosition.Weakness) != null) {
            weakness = (Weakness) arr.get(StatusArrayPosition.Weakness);
        }
        if (arr.get(StatusArrayPosition.StatusEffect) != null) {
            statusEffect = (StatusEffect) arr.get(StatusArrayPosition.StatusEffect);
        }
    }

    public StatusArray getStats() {
        StatusArray arr = new StatusArray();
        arr.modify(StatusArrayPosition.Health, health);
        arr.modify(StatusArrayPosition.Mana, mana);
        arr.modify(StatusArrayPosition.ATK, attack);
        arr.modify(StatusArrayPosition.DEF, defense);
        arr.modify(StatusArrayPosition.Luck, luck);
        arr.modify(StatusArrayPosition.MATK, mAttack);
        arr.modify(StatusArrayPosition.MDEF, mDefense);
        return arr;
    }

    public void giveExp(int exp) {
        this.exp += exp;
    }

    public void levelUp() {
        if (this.level >= 99) {
            System.out.println((this.name.equalsIgnoreCase("you") == true) ? "You are max level!" : this.name + " is max level!");
        }

        if (this.exp >= this.expToNextLevel) {
            int levelNeeded = 1;
            while (this.exp >= this.expToNextLevel) {
                this.exp -= this.expToNextLevel;
                this.expToNextLevel = (MathFunc.randomInt() + (3 * level));
                levelNeeded++;
            }

            System.out.println((this.name.equalsIgnoreCase("you") == true) ? "You have leveled up!" : this.name + " has leveld up!");

            ConsoleFunc.wait(2000);

            //TODO: level up
        }
    }

}