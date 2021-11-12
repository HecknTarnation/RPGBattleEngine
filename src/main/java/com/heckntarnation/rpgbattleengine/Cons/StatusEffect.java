package com.heckntarnation.rpgbattleengine.Cons;

import java.io.Serializable;

/**
 * This class should be complete made by you, the only calls by the engine will
 * be to the tick and setCharacter methods. It will also use the shouldbeRemoved
 * boolean, and the namespace + id strings. Feel free to add any variables and
 * methods in your implementation.
 *
 * @author Ben
 */
public class StatusEffect implements Serializable {

    /**
     * For JSON loading
     */
    public String namespace, id;

    public Player character;
    public boolean shouldBeRemoved;

    public StatusEffect() {

    }

    /**
     * Main status effect code (should be overridden)
     */
    public void tick() {

    }

    /**
     * Sets the character this is attached to
     *
     * @param character
     */
    public void setCharacter(Player character) {
        this.character = character;
    }

}
