package com.coolninja.rpgengine.Cons;

/**
 * This class should be complete made by you, the only calls by the engine will
 * be to the tick and setCharacter method. It also used the shouldbeRemoved
 * boolean. Feel free to add any variables and methods in you implementation.
 *
 * @author Ben
 */
public class StatusEffect {

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
