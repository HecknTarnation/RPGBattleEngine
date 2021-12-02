package com.heckntarnation.rpgbattleengine.cons;

import com.heckntarnation.rpgbattleengine.arrays.StatusArray;
import com.heckntarnation.rpgbattleengine.enums.EquipSlot;
import com.heckntarnation.rpgbattleengine.enums.StatusArrayPosition;
import java.io.Serializable;

/**
 *
 * @author Ben
 */
public class Equipment extends Item implements Serializable {

    public int maxHealth, maxMana, attack, defense, luck, mAttack, mDefense;
    public Weakness weakness;
    public EquipSlot slot;

    public Equipment(String name, EquipSlot slot) {
        super(name);
        this.slot = slot;
    }

    public Equipment(String name, String desc, int maxHealth, int maxMana, int attack, int defense, int luck, int mAttack, int mDefense, EquipSlot slot) {
        super(name);
        this.setDesc(desc);
        this.setHealthBoost(maxHealth);
        this.setManaBoost(maxMana);
        this.setAttackBoost(attack);
        this.setDefenseBoost(defense);
        this.setLuckBoost(luck);
        this.setMAttackBoost(mAttack);
        this.setMDefenseBoost(mDefense);
        this.slot = slot;
    }

    public EquipSlot getSlot() {
        return this.slot;
    }

    public Equipment setHealthBoost(int maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    public Equipment setManaBoost(int maxMana) {
        this.maxMana = maxMana;
        return this;
    }

    public Equipment setAttackBoost(int attack) {
        this.attack = attack;
        return this;
    }

    public Equipment setDefenseBoost(int defense) {
        this.defense = defense;
        return this;
    }

    public Equipment setMAttackBoost(int mAttack) {
        this.mAttack = mAttack;
        return this;
    }

    public Equipment setLuckBoost(int luck) {
        this.luck = luck;
        return this;
    }

    public Equipment setMDefenseBoost(int mDefense) {
        this.mDefense = mDefense;
        return this;
    }

    public Equipment setWeakness(Weakness weakness) {
        this.weakness = weakness;
        return this;
    }

    public StatusArray getBoosts() {
        StatusArray arr = new StatusArray();
        arr.put(StatusArrayPosition.Health, maxHealth);
        arr.put(StatusArrayPosition.Mana, maxMana);
        arr.put(StatusArrayPosition.ATK, attack);
        arr.put(StatusArrayPosition.DEF, defense);
        arr.put(StatusArrayPosition.Luck, luck);
        arr.put(StatusArrayPosition.MATK, mAttack);
        arr.put(StatusArrayPosition.MDEF, mDefense);
        arr.put(StatusArrayPosition.Weakness, weakness);
        return arr;
    }

}
