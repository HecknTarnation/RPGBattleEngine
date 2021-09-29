package com.heckntarnation.rpgbattleengine.Cons;

import java.io.Serializable;

/**
 *
 * @author Ben
 */
public class Item implements Serializable {

    /**
     * For JSON loading
     */
    public String namespace, id;

    public String name, desc;

    public Item(String name) {

    }

    public Item setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public void Use() {

    }

}