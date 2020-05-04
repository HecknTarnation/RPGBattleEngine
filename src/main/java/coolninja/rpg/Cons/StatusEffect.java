package coolninja.rpg.Cons;

/**
 *
 * @author Ben Ballard
 * @since 1.0
 * @version 1.0
 */
public class StatusEffect {

    public int turnDuration;
    public IEffect startOfEffect, endOfTurn, endOfEffect;

    /**
     * Creates a new StatusEffect object. startOfEffect is run when this object
     * is created.
     *
     * @param turnDuration
     * @param startOfEffect
     * @param endOfTurn
     * @param endOfEffect
     * @param characterAttached
     */
    public StatusEffect(int turnDuration, IEffect startOfEffect, IEffect endOfTurn, IEffect endOfEffect, Object characterAttached) {
        this.turnDuration = turnDuration;
        this.startOfEffect = startOfEffect;
        this.endOfTurn = endOfTurn;
        this.endOfEffect = endOfEffect;
        this.startOfEffect.effect();
    }

    /**
     * This function is run after everyone, including the enemies, have had
     * their turn. It's run beginning with the player, companions, then enemies.
     * <br>
     * Do not call manually (it's not a problem if you do, it's just not useful
     * outside of battle).
     *
     * @since 1.0
     */
    public void endOfTurn() {
        this.turnDuration--;
        this.endOfTurn.effect();
        if (turnDuration < 0) {
            endOfEffect.effect();
        }
    }

}
