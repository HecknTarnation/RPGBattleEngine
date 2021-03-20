package com.coolninja.rpgengine.enums;

/**
 *
 * @author Ben
 */
public enum EN_USKeys {
    err_invalidlang("err_invalidlang"),
    err_missinglangkey("err_missinglangkey"),
    battle_compidle("battle_compidle"),
    battle_plridle("battle_plridle"),
    battle_menu("battle_menu"),
    battle_currentTurn("battle_currentTurn");

    public String key;

    private EN_USKeys(String key) {
        this.key = key;
    }
}
