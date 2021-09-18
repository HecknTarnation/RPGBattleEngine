package com.coolninja.rpgengine.Cons;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Weakness implements Serializable {

    public static boolean isWeak(ArrayList<Weakness> weakness, Move move) {
        if (weakness.stream().anyMatch(w -> (w.type.equalsIgnoreCase(move.type.type)))) {
            return true;
        }
        return false;
    }

    //ex: "fire", "water"
    public String type;
    public float effectiveness;

    /**
     * Creates a new weakness, this is used for this formula: <br>
     * if damage.type = weakness.type, <br>
     * then damage * effectiveness = final damage
     *
     * <br>
     * If effectiveness {@literal <} 0 it will be treated as a resistence.
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
