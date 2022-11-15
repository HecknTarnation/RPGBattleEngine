package com.heckntarnation.rpgbattleengine.cons.Battle;

import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;
import com.heckntarnation.rpgbattleengine.cons.Characters.Player;
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

    public String tickScript;

    /**
     * For HeckScript Integration loading
     */
    public String namespace, id;

    public Object character;
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

    /**
     * Sets the character this is attached to
     *
     * @param character
     */
    public void setCharacter(Enemy character) {
        this.character = character;
    }

}
