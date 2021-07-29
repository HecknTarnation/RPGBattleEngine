package com.coolninja.rpgengine.Cons;

import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.Engine;
import com.coolninja.rpgengine.MathFunc;
import com.coolninja.rpgengine.Vars;
import com.coolninja.rpgengine.arrays.StatusArray;
import static com.coolninja.rpgengine.dev.Macros.*;
import static com.coolninja.rpgengine.enums.LangKeys.*;
import com.coolninja.rpgengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Player implements Serializable {

    /**
     * {healthgrowthRate, manaGrowhRate, attackGrowthRate, defenseGrowthRate,
     * luckGrowthRate, mAttackGrowthRate, mDefenseGrowthRate};
     */
    public double[] growthRates = {1.0, 1.0, 1.0, 1.0, 0.1, 1.0, 1.0};
    public int baseExp = 50;
    public int expMod = 1;

    public String name;
    public int level, health, maxHealth, mana, maxMana, attack, defense, luck, mAttack, mDefense, exp, expToNextLevel = baseExp;

    /**
     * Player's moves
     */
    public ArrayList<Move> moves = new ArrayList<>();

    /**
     * 0 = feet, 1 = legs, 2 = arms, 3 = chest, 4 = head, 5 = weapon, 6 = mod
     */
    public Equipment[] equipment = new Equipment[7];

    /**
     * Player inventory
     */
    public ArrayList<Item> inv = new ArrayList<>();

    public Weakness weakness;
    public StatusEffect statusEffect;

    public Player(String name, int baseExp, int expmod) {
        this.name = name;
        this.baseExp = baseExp;
        this.expMod = expmod;
    }

    /**
     * Is used for loading saved data.
     *
     * @param arr
     * @param level
     * @param growthRates
     * @param moves
     * @param inv
     * @param exp
     * @param expNextLevel
     */
    public void load(StatusArray arr, int level, double[] growthRates, ArrayList<Move> moves, ArrayList<Item> inv, int exp, int expNextLevel) {
        this.setStats(arr);
        this.maxHealth = (Integer) arr.get(StatusArrayPosition.MaxHealth);
        this.maxMana = (Integer) arr.get(StatusArrayPosition.MaxMana);
        this.growthRates = growthRates;
        this.moves = moves;
        this.inv = inv;
        this.exp = exp;
        this.expToNextLevel = expNextLevel;
    }

    public Player setStats(StatusArray arr) {
        if (arr.get(StatusArrayPosition.Health) != null) {
            health = (int) arr.get(StatusArrayPosition.Health);
        }
        if (arr.get(StatusArrayPosition.MaxHealth) != null) {
            maxHealth = (int) arr.get(StatusArrayPosition.MaxHealth);
        }
        if (arr.get(StatusArrayPosition.MaxMana) != null) {
            maxMana = (int) arr.get(StatusArrayPosition.MaxMana);
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
        return this;
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

    public void levelUp(int exp) {
        this.giveExp(exp);
        levelUp();
    }

    //TODO: localize this method
    public void levelUp() {
        if (this.level >= Vars.maxLevel) {
            System.out.println((this.name.equalsIgnoreCase("you") == true) ? "You are max level!" : this.name + " is max level!");
            return;
        }

        int[] oldStats = {this.maxHealth, this.maxMana, this.attack, this.mAttack, this.luck, this.defense, this.mDefense};

        if (this.exp >= this.expToNextLevel) {
            int levelNeeded = 0;
            while (this.exp >= this.expToNextLevel) {
                this.exp -= this.expToNextLevel;
                this.expToNextLevel = (MathFunc.expToNextLevel(level, this.baseExp, this.expMod));
                levelNeeded++;
            }

            System.out.println((this.name.equalsIgnoreCase("you") == true) ? "You have leveled up!" : this.name + " has leveld up!");

            ConsoleFunc.wait(2000);

            int[] newStats = oldStats.clone();
            for (int i = 0; i < 7; i++) {
                newStats[i] = MathFunc.statIncrease(oldStats[i], levelNeeded, growthRates[i]);
            }
            System.out.println(
                    localize(stat_health) + ": " + oldStats[0] + " => " + newStats[0] + "\n"
                    + localize(stat_mana) + ": " + oldStats[1] + " => " + newStats[1] + "\n"
                    + localize(stat_attack) + ": " + oldStats[2] + " => " + newStats[2] + "\n"
                    + localize(stat_mAttack) + ": " + oldStats[3] + " => " + newStats[3] + "\n"
                    + localize(stat_luck) + ": " + oldStats[4] + " => " + newStats[4] + "\n"
                    + localize(stat_defense) + ": " + oldStats[5] + " => " + newStats[5] + "\n"
                    + localize(stat_mDefense) + ": " + oldStats[6] + " => " + newStats[6] + "\n"
                    + localize(stat_expNeeded) + ": " + exp + "/" + expToNextLevel + "\n"
            );

            ConsoleFunc.wait(4000);

            int statPoints = 5;
            while (statPoints > 0) {
                String[] str = {
                    localize(stat_health),
                    localize(stat_mana),
                    localize(stat_attack),
                    localize(stat_mAttack),
                    localize(stat_luck),
                    localize(stat_defense),
                    localize(stat_mDefense)
                };
                int index = Engine.inputHandler.doMenu(str, String.format(localize(stat_point), statPoints), true);
                newStats[index]++;
                statPoints--;
            }
            maxHealth = newStats[0];
            maxMana = newStats[1];
            attack = newStats[2];
            mAttack = newStats[3];
            luck = newStats[4];
            defense = newStats[5];
            mDefense = newStats[6];

            //this isn't working correctly for some reason
            health = maxMana;
            mana = maxMana;

            Engine.inputHandler.waitUntilEnter();
        }
    }

    public Move[] getMoves() {
        Move[] m = new Move[moves.size()];
        return this.moves.toArray(m);
    }

    public String[] invToStringArr() {
        String[] temp = new String[]{};
        for (int i = 0; i < inv.size(); i++) {
            temp[i] = inv.get(i).name;
        }
        return temp;
    }

}
