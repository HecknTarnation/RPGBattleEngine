package com.heckntarnation.rpgbattleengine.enums;

/**
 * List of equipment slots
 *
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public enum EquipSlot {

    Feet((byte) 0), Legs((byte) 1), Arms((byte) 2), Chest((byte) 3), Head((byte) 4), Weapon((byte) 5), Accessory((byte) 6);

    public byte index;

    //index is where it belongs in the equipment array
    private EquipSlot(byte index) {
        this.index = index;
    }

}
