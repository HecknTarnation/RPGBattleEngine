package com.coolninja.rpgengine.enums;

/**
 *
 * @author Ben
 */
public enum StatusArrayPosition {

    Health(0), Mana(1), ATK(2), DEF(3), Luck(4), MATK(5), MDEF(6), Weakness(7), AILevel(8), StatusEffect(9), MaxHealth(10), MaxMana(11), Evasion(12);
    public int actualPos;

    private StatusArrayPosition(int p) {
        this.actualPos = p;
    }
}
