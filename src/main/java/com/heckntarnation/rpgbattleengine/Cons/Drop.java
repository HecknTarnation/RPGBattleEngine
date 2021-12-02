package com.heckntarnation.rpgbattleengine.cons;

import com.heckntarnation.rpgbattleengine.MathFunc;
import java.io.Serializable;

/**
 *
 * @author Ben
 */
public class Drop implements Serializable {

    /**
     * For JSON loading
     */
    public String namespace, id;

    public Item item;
    /**
     * The chance to get this item. Ex: 1/128
     */
    public String chance;

    public Drop(Item item, String chance) {
        this.item = item;
        this.chance = chance;
    }

    public Drop setItem(Item i) {
        this.item = i;
        return this;
    }

    public Item getItem() {
        return this.item;
    }

    public Drop setChance(String chance) {
        this.chance = chance;
        return this;
    }

    public Drop setChance(int numerator, int denominator) {
        this.chance = numerator + "/" + denominator;
        return this;
    }

    public String getChance() {
        return this.chance;
    }

    public boolean getIfShouldDrop() {
        String[] c = this.chance.split("/");
        int[] amount = new int[]{Integer.parseInt(c[1]), Integer.parseInt(c[1]) - Integer.parseInt(c[0])};
        String[] elements = new String[]{"get", "miss"};
        return ((String) MathFunc.hatpull(amount, elements)).equalsIgnoreCase("get");
    }

}
