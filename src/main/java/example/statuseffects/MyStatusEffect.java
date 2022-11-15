package example.statuseffects;

import com.heckntarnation.rpgbattleengine.cons.Battle.StatusEffect;
import com.heckntarnation.rpgbattleengine.cons.Characters.Enemy;

/**
 *
 * @author Ben
 */
//Copying the "Posion" effect class, with a change to make it for enemies.
public class MyStatusEffect extends StatusEffect {

    public int duration, tickDamage;

    public MyStatusEffect(int duration, int tickDamage) {
        this.duration = duration;
        this.tickDamage = tickDamage;
    }

    @Override
    public void tick() {
        //Make the attached [character], in this case an enemy, take damage.
        ((Enemy) character).health -= tickDamage;
        System.out.println(((Enemy) character).name + " took " + tickDamage + " damage.");
        //decrease the duration.
        duration--;
        //if duration is zero...
        if (duration == 0) {
            //...set "shouldBeRemoved". Setting this variable to true will remove the status effect.
            this.shouldBeRemoved = true;
        }
    }

}
