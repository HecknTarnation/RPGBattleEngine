package com.heckntarnation.rpgbattleengine.statusEffects;

import com.heckntarnation.rpgbattleengine.Cons.StatusEffect;

/**
 * This is an example of how a status effect should be created
 *
 * @author Ben
 */
public class Posion extends StatusEffect {

    public int duration, tickDamage;

    public Posion(int duration, int tickDamage) {
        this.duration = duration;
        this.tickDamage = tickDamage;
    }

    @Override
    public void tick() {
        character.health -= tickDamage;
        System.out.println(character.name + " took " + tickDamage + "damage.");
        duration--;
        if (duration == 0) {
            this.shouldBeRemoved = true;
        }
    }

}
