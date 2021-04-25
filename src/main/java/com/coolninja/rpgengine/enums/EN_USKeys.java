package com.coolninja.rpgengine.enums;

/**
 *
 * @author Ben
 */
public enum EN_USKeys {
    err_invalidlang("err_invalidlang"),
    err_missinglangkey("err_missinglangkey"),
    battle_idle("battle_idle"),
    battle_menu("battle_menu"),
    battle_currentTurn("battle_currentTurn"),
    /**
     * "[p1] used [p2] and hit [p3] for [p4] damage!"
     */
    battle_moveused("battle_moveused"),
    /**
     * "[p1 (name + doesn't/don't)] have enough mana!"
     */
    battle_missingmana("battle_missingmana"),
    /**
     * "[p1] missed"
     */
    battle_missed("battle_missed"),
    battle_critical("battle_critical"),
    inv_noitems("inv_noitems"),
    gen_your("gen_your");

    public String key;

    private EN_USKeys(String key) {
        this.key = key;
    }
}
