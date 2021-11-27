package com.heckntarnation.rpgbattleengine.Cons;

import com.heckntarnation.rpgbattleengine.ConsoleFunc;
import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.MathFunc;
import com.heckntarnation.rpgbattleengine.Vars;
import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import static com.heckntarnation.rpgbattleengine.dev.Macros.*;
import static com.heckntarnation.rpgbattleengine.enums.LangKeys.*;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Player implements Serializable {

    /**
     * For JSON loading
     */
    public String namespace, id;

    /**
     * {healthgrowthRate, manaGrowhRate, attackGrowthRate, defenseGrowthRate,
     * luckGrowthRate, mAttackGrowthRate, mDefenseGrowthRate};
     */
    public double[] growthRates = {1.0, 1.0, 1.0, 1.0, 0.1, 1.0, 1.0};
    /**
     * The base requirement for experience to level up.
     */
    public int baseExp = 50;
    public float expMod = 1.5f;

    public String name;
    public int level = 1, health, maxHealth, mana, maxMana, attack, defense, luck, mAttack, mDefense, exp, expToNextLevel;
    /**
     * Chance of dodging an attack
     */
    public float evasion = 0.0f;

    /**
     * Player's moves
     */
    public ArrayList<Move> moves = new ArrayList<>();

    /**
     * 0 = feet, 1 = legs, 2 = arms, 3 = chest, 4 = head, 5 = weapon, 6 =
     * accessory
     */
    public Equipment[] equipment = new Equipment[7];

    /**
     * Player's inventory
     */
    public ArrayList<Item> inv = new ArrayList<>();

    public ArrayList<Weakness> weakness;
    public ArrayList<StatusEffect> statusEffects;

    public Player(String name, int baseExp, int expmod) {
        this.name = name;
        this.baseExp = baseExp;
        this.expMod = expmod;
        this.expToNextLevel = this.baseExp;
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
            weakness = (ArrayList<Weakness>) arr.get(StatusArrayPosition.Weakness);
        }
        if (arr.get(StatusArrayPosition.StatusEffect) != null) {
            statusEffects = (ArrayList<StatusEffect>) arr.get(StatusArrayPosition.StatusEffect);
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

    public void levelUp() {
        if (this.level >= Vars.maxLevel) {
            System.out.println((this.name.equalsIgnoreCase(BattleEngine.localizationHandler.SECOND_PERSON_STRING) == true) ? localize(stat_maxlevelsecondperson) : String.format(this.name, stat_maxlevel));
            return;
        }

        int[] oldStats = {this.maxHealth, this.maxMana, this.attack, this.mAttack, this.luck, this.defense, this.mDefense};

        if (this.exp >= this.expToNextLevel) {
            int levelNeeded = 0;
            while (this.exp >= this.expToNextLevel) {
                this.exp -= this.expToNextLevel;
                this.expToNextLevel = MathFunc.expToNextLevel(this.level, this.baseExp, this.expMod);
                this.level++;
                levelNeeded++;
            }

            System.out.println((this.name.equalsIgnoreCase("you") == true) ? localize(stat_secondpersonlevelup) : String.format(this.name, localize(stat_levelup)));

            ConsoleFunc.wait(2000);

            int[] newStats = oldStats.clone();
            for (int i = 0; i < 7; i++) {
                newStats[i] = MathFunc.statIncrease(oldStats[i], levelNeeded, growthRates[i]);
            }
            System.out.println(
                    localize(stat_health) + oldStats[0] + " => " + newStats[0] + "\n"
                    + localize(stat_mana) + oldStats[1] + " => " + newStats[1] + "\n"
                    + localize(stat_attack) + oldStats[2] + " => " + newStats[2] + "\n"
                    + localize(stat_mAttack) + oldStats[3] + " => " + newStats[3] + "\n"
                    + localize(stat_luck) + oldStats[4] + " => " + newStats[4] + "\n"
                    + localize(stat_defense) + oldStats[5] + " => " + newStats[5] + "\n"
                    + localize(stat_mDefense) + oldStats[6] + " => " + newStats[6] + "\n"
                    + localize(stat_expNeeded) + exp + "/" + expToNextLevel + "\n"
            );

            BattleEngine.inputHandler.waitUntilEnter();
            ConsoleFunc.clear();

            int statPoints = Vars.skillPointsOnLevel * levelNeeded;
            while (statPoints > 0) {
                String[] str = {
                    localize(stat_maxHealth) + ": " + newStats[0],
                    localize(stat_maxMana) + ": " + newStats[1],
                    localize(stat_attack) + ": " + newStats[2],
                    localize(stat_mAttack) + ": " + newStats[3],
                    localize(stat_luck) + ": " + newStats[4],
                    localize(stat_defense) + ": " + newStats[5],
                    localize(stat_mDefense) + ": " + newStats[6]
                };
                int index = BattleEngine.inputHandler.doMenu(str, String.format(localize(stat_point), statPoints), true);
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

            health = maxHealth;
            mana = maxMana;
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

    public void statusEffectTick() {
        if (statusEffects.isEmpty()) {
            return;
        }
        statusEffects.forEach(effect -> {
            effect.tick();
            statusEffects.removeIf(filter -> {
                return effect.shouldBeRemoved;
            });
        });
    }

    public void equip(Equipment equip) {
        if (equipment[equip.slot.index] == null) {
            maxHealth += equip.maxHealth;
            maxMana += equip.maxMana;
            attack += equip.attack;
            defense += equip.defense;
            luck += equip.luck;
            mAttack += equip.mAttack;
            mDefense += equip.mDefense;
            weakness.add(equip.weakness);
            equipment[equip.slot.index] = equip;
        } else {
            maxHealth -= equipment[equip.slot.index].maxHealth;
            maxMana -= equipment[equip.slot.index].maxMana;
            attack -= equipment[equip.slot.index].attack;
            defense -= equipment[equip.slot.index].defense;
            luck -= equipment[equip.slot.index].luck;
            mAttack -= equipment[equip.slot.index].mAttack;
            mDefense -= equipment[equip.slot.index].mDefense;
            weakness.remove(equipment[equip.slot.index].weakness);
            equipment[equip.slot.index] = null;
            equip(equip);
        }

    }

}
