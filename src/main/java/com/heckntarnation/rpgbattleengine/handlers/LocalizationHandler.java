package com.heckntarnation.rpgbattleengine.handlers;

import com.heckntarnation.rpgbattleengine.Colors;
import com.heckntarnation.rpgbattleengine.ConsoleFunc;
import com.heckntarnation.rpgbattleengine.enums.LangKeys;
import static com.heckntarnation.rpgbattleengine.enums.LangKeys.*;
import com.heckntarnation.rpgbattleengine.exceptions.StringAlreadyDefinedException;
import java.util.HashMap;

/**
 * A handler for localization support.
 *
 * @author Ben
 */
public class LocalizationHandler {

    /**
     * This denotes wether or not to use second person versions of strings, such
     * as stat_secondpersonlevelup. You should set the player's name to this
     * value to do this.
     */
    public final String SECOND_PERSON_STRING = "SECOND_PERSON";

    //this is probably, no definatly, really memory inefficient but I can't think of a better solution.
    public HashMap<String, HashMap<String, String>> langs = new HashMap<>();

    private final HashMap<String, String> en_us = new HashMap<>();

    private HashMap<String, String> currentLang;

    /**
     * Initializes this handler, also creates the en_us language (the default
     * language)
     */
    public void init() {

        //init en_us
        en_us.put(err_invalidlang.key, "Invalid Language");
        en_us.put(err_missinglangkey.key, "Missing or Invalid Key, returning null...");
        en_us.put(battle_idle.key, "%s did nothing");
        en_us.put(battle_menu.key, "Attack,Item,Idle,Run");
        en_us.put(battle_currentTurn.key, "It's %s's turn");
        en_us.put(battle_moveused.key, "%1$s used %2$s and hit %3$s for %4$s damage!");
        en_us.put(battle_moveusedAlly.key, "%1$s used %2$s on %3$s.");
        en_us.put(battle_missingmana.key, "%s doesn't have enough mana!");
        en_us.put(battle_ran.key, "%s ran.");
        en_us.put(battle_failedToRun.key, "%s couldn't get away!");
        en_us.put(battle_missed.key, "%s missed!");
        en_us.put(battle_critical.key, Colors.BLUE + "C" + Colors.RED + " R" + Colors.YELLOW + " I" + Colors.PURPLE + " T" + Colors.RED + " I" + Colors.CYAN + " C" + Colors.GREEN + " A" + Colors.BLACK + " L" + Colors.reset());
        en_us.put(inv_noitems.key, "%s have no items.");
        en_us.put(battle_gotitem.key, "%1$s received %2$s");
        en_us.put(battle_gameover.key, "Game Over");
        en_us.put(stat_maxlevelsecondperson.key, "You are max level!");
        en_us.put(stat_maxlevel.key, "%s is max level!");
        en_us.put(stat_secondpersonlevelup.key, "You have leveled up!");
        en_us.put(stat_levelup.key, "%s has leveled up!");
        en_us.put(stat_health.key, "Health: ");
        en_us.put(stat_mana.key, "Mana: ");
        en_us.put(stat_maxHealth.key, "Max Health: ");
        en_us.put(stat_maxMana.key, "Max Mana: ");
        en_us.put(stat_attack.key, "Attack: ");
        en_us.put(stat_mAttack.key, "Magic Attack: ");
        en_us.put(stat_defense.key, "Defense: ");
        en_us.put(stat_mDefense.key, "Magic Defense: ");
        en_us.put(stat_luck.key, "Luck: ");
        en_us.put(stat_point.key, "Stat Points: %s");
        en_us.put(stat_expNeeded.key, "Exp to next level: ");
        en_us.put(press_submit.key, "Press [Submit]...");
        //end init en_us
        langs.put("en_us", en_us);

    }

    public void changLang(String langKey) {
        if (langs.containsKey(langKey)) {
            currentLang = langs.get(langKey);
        } else {
            System.out.println(getLocalizedString(err_invalidlang));
            ConsoleFunc.wait(1000);
        }
    }

    public void addLang(String key, HashMap<String, String> lang) {
        langs.put(key, lang);
    }

    public void addString(String key, String string) throws StringAlreadyDefinedException {
        if (currentLang.get(key) != null) {
            throw new StringAlreadyDefinedException(key);
        }
        currentLang.put(key, string);
    }

    public String getLocalizedString(LangKeys key) {
        return getLocalizedString(key.key);
    }

    public String getLocalizedString(String key) {
        if (!currentLang.containsKey(key)) {
            System.out.println(getLocalizedString(err_missinglangkey));
            return null;
        }
        return currentLang.get(key);
    }

    /**
     * Same as getLocalizedString, but does not print an error.
     *
     * @param key
     * @return
     */
    public String getLocalizedString_surpressed(String key) {
        if (!currentLang.containsKey(key)) {
            return null;
        }
        return currentLang.get(key);
    }

}
