package com.heckntarnation.rpgbattleengine.cons.Battle;

import com.heckntarnation.rpgbattleengine.Colors;
import java.io.Serializable;
import java.net.URI;

/**
 *
 * @author Ben
 */
public class Move implements Serializable {

    public static String[] arrToStr(Move[] moves) {
        String[] s = new String[moves.length];
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].manaCost > 0) {
                s[i] = moves[i].name + " | " + Colors.BLUE + "Mana: " + moves[i].manaCost + Colors.reset();
            }
            s[i] = moves[i].name;
        }
        return s;
    }

    /**
     * For JSON loading
     */
    public String namespace, id;

    public String name;
    public int damage, mDamage, manaCost;
    public float accuracy;
    public Weakness type;
    public Graphic graphic;
    public URI sound;

    public Move(String name) {
        this.name = name;
        this.accuracy = 1.0f;
    }

    public Move setDamage(int damage, int mDamage) {
        this.damage = damage;
        this.mDamage = mDamage;
        return this;
    }

    public Move setGraphic(Graphic g) {
        this.graphic = g;
        return this;
    }

    public Move setAccuracy(int acc) {
        this.accuracy = acc;
        return this;
    }

    public int[] getDamage() {
        return new int[]{this.damage, this.mDamage};
    }

    public Move setManaCost(int manaCost) {
        this.manaCost = manaCost;
        return this;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public Graphic getGraphic() {
        return this.graphic;
    }

    public Move setSound(URI sound) {
        this.sound = sound;
        return this;
    }

    public URI getSound() {
        return this.sound;
    }

    public Move setType(Weakness type) {
        this.type = type;
        return this;
    }

    public Weakness getType() {
        return this.type;
    }

    /**
     * Should be overridden
     */
    public void Use() {

    }

}
