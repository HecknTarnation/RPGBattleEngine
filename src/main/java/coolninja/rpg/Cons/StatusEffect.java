package coolninja.rpg.Cons;

/**
 *
 * @author Ben Ballard
 * @since 1.0
 * @version 1.0
 */
public class StatusEffect {

    public int turnDuration;
    public IEffect startOfEffect, endOfEffect;

    /**
     * Creates a new StatusEffect object. startOfEffect is run when this object
     * is created.
     *
     * @param turnDuration
     * @param startOfEffect
     * @param endOfEffect
     */
    public StatusEffect(int turnDuration, IEffect startOfEffect, IEffect endOfEffect) {
        this.turnDuration = turnDuration;
        this.startOfEffect = startOfEffect;
        this.endOfEffect = endOfEffect;
        this.startOfEffect.effect();
    }

    /**
     * This function is run after everyone, including the enemies, have had
     * their turn. It's run beginning with the player, companions, then enemies.
     *
     * @since 1.0
     */
    public void endOfTurn() {
        this.turnDuration--;
        if (turnDuration < 0) {
            endOfEffect.effect();
        }
    }

}
