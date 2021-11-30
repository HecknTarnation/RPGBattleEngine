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

    public static EquipSlot getFromString(String slot) {
        switch (slot) {
            case "feet": {
                return EquipSlot.Feet;
            }
            case "arms": {
                return EquipSlot.Arms;
            }
            case "chest": {
                return EquipSlot.Chest;
            }
            case "legs": {
                return EquipSlot.Legs;
            }
            case "head": {
                return EquipSlot.Head;
            }
            case "accessory": {
                return EquipSlot.Accessory;
            }
            case "weapon": {
                return EquipSlot.Weapon;
            }
            default: {
                return null;
            }
        }
    }

}
