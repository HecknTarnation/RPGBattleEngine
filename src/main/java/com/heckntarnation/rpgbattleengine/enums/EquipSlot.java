package com.heckntarnation.rpgbattleengine.enums;

/**
 * List of equipment slots
 *
 * @version 1.0
 * @since 1.0
 * @author Ben Ballard
 */
public enum EquipSlot {

    Feet(0), Legs(1), Arms(2), Chest(3), Head(4), Weapon(5), Accessory(6);

    public int index;

    //index is where it belongs in the equipment array
    private EquipSlot(int index) {
        this.index = index;
    }

}
