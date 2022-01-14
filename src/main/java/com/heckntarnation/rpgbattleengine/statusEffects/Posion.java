package com.heckntarnation.rpgbattleengine.statusEffects;

import com.heckntarnation.rpgbattleengine.cons.Characters.Player;
import com.heckntarnation.rpgbattleengine.cons.Battle.StatusEffect;

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
        ((Player) character).health -= tickDamage;
        System.out.println(((Player) character).name + " took " + tickDamage + "damage.");
        duration--;
        if (duration == 0) {
            this.shouldBeRemoved = true;
        }
    }

}
