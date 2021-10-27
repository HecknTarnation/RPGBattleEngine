package com.heckntarnation.rpgbattleengine.enums;

/**
 *
 * @author Ben
 */
public enum StatusArrayPosition {

    Health((byte) 0), Mana((byte) 1), ATK((byte) 2), DEF((byte) 3), Luck((byte) 4), MATK((byte) 5), MDEF((byte) 6),
    Weakness((byte) 7), AILevel((byte) 8), StatusEffect((byte) 9), MaxHealth((byte) 10), MaxMana((byte) 11), Evasion((byte) 12);

    public byte actualPos;

    private StatusArrayPosition(byte p) {
        this.actualPos = p;
    }
}
