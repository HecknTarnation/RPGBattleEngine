package com.heckntarnation.rpgbattleengine.cons.Items;

import java.io.Serializable;

/**
 *
 * @author Ben
 */
public class Item implements Serializable {

    /**
     * For HeckScript Integration loading
     */
    public String namespace, id;

    public String name, desc;

    public Item(String name) {
        this.name = name;
    }

    public Item setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    /**
     * This function should be overridden.
     */
    public void Use() {

    }

}
