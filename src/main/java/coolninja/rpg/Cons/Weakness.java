package coolninja.rpg.Cons;

import coolninja.rpg.WeaknessType;

/**
 * Weakness object
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public class Weakness {
    
    public WeaknessType type;
    /**
     * How much the base damage is multiplied by (if less than one, it is considered a resistance)
     */
    public double effectiveness;
    
    public Weakness(WeaknessType type, double effectiveness){
        this.type = type;
        this.effectiveness = effectiveness;
    }
    
}
