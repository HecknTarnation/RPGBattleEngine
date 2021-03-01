package com.coolninja.rpgengine.Cons;

/**
 *
 * @author Ben
 */
public class Weakness {

    //ex: "fire", "water"
    public String type;
    public float effectiveness;

    /**
     * Creates a new weakness, this is used for this formula: <br>
     * if damage.type = weakness.type, <br>
     * then damage * effectiveness = final damage
     *
     * @param type
     * @param effectiveness
     */
    public Weakness(String type, float effectiveness) {
        this.type = type;
        this.effectiveness = effectiveness;
    }

    public Weakness setType(String type) {
        this.type = type;
        return this;
    }

    public Weakness setEffectiveness(float eff) {
        this.effectiveness = eff;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public float getEffectiveness() {
        return this.effectiveness;
    }

}
