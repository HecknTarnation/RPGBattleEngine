package com.coolninja.rpgengine.Cons;

import java.io.Serializable;

/**
 *
 * @author Ben
 */
public class Item implements Serializable {

    public String name, desc;
    public float chance;

    public Item(String name) {

    }

    public Item setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public Item setChance(float chance) {
        this.chance = chance;
        return this;
    }

    public float getChance() {
        return this.chance;
    }

    public void Use() {

    }

}
