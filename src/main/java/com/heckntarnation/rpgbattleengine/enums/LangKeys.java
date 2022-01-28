package com.heckntarnation.rpgbattleengine.enums;

/**
 *
 * @author Ben
 */
public enum LangKeys {
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
     * "[p1] doesn't have enough mana!"
     */
    battle_missingmana("battle_missingmana"),
    /**
     * "You don't have enough mana!"
     */
    battle_missingmana_2ndp("battle_missingmana_2ndp"),
    /**
     * "[p1] missed"
     */
    battle_missed("battle_missed"),
    battle_critical("battle_critical"),
    /**
     * "[p1] ran."
     */
    battle_ran("battle_ran"),
    /**
     * "[p1] couldn't get away!"
     */
    battle_failedToRun("battle_failedToRun"),
    /**
     * "[name] received [item]"
     */
    battle_gotitem("battle_gotitem"),
    battle_gameover("battle_gameover"),
    inv_noitems("inv_noitems"),
    //Stats section
    stat_secondpersonlevelup("stat_secondpersonlevelup"),
    stat_levelup("stat_levelup"),
    stat_maxlevelsecondperson("stat_maxlevelsecondperson"),
    stat_maxlevel("stat_maxlevel"),
    stat_health("stat_health"),
    stat_mana("stat_mana"),
    stat_maxHealth("stat_maxHealth"),
    stat_maxMana("stat_maxMana"),
    stat_attack("stat_attack"),
    stat_defense("stat_defense"),
    stat_mAttack("stat_mAttack"),
    stat_mDefense("stat_mDefense"),
    stat_luck("stat_luck"),
    stat_point("stat_point"),
    stat_expNeeded("stat_expNeeded");

    public String key;

    private LangKeys(String key) {
        this.key = key;
    }
}
