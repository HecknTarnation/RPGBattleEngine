package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Colors;
import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.enums.LangKeys;
import static com.coolninja.rpgengine.enums.LangKeys.*;
import java.util.HashMap;

/**
 * TODO: Make sure I don't use localize(key) + string. Because that will cause
 * problems.
 *
 * @author Ben
 */
public class LocalizationHandler {

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
        en_us.put(battle_missingmana.key, "%s doesn't have enough mana!");
        en_us.put(gen_your.key, "your");
        en_us.put(gen_firstppronoun.key, "you");
        en_us.put(battle_missed.key, "%s missed!");
        en_us.put(battle_critical.key, Colors.BLUE + "C" + Colors.RED + " R" + Colors.YELLOW + " I" + Colors.PURPLE + " T" + Colors.RED + " I" + Colors.CYAN + " C" + Colors.GREEN + " A" + Colors.BLACK + " L" + Colors.reset());
        en_us.put(inv_noitems.key, "%s have no items.");
        en_us.put(battle_gotitem.key, "%1$s received %2$s");
        en_us.put(battle_gameover.key, "Game Over");
        en_us.put(stat_health.key, "Health");
        en_us.put(stat_mana.key, "Mana");
        en_us.put(stat_maxHealth.key, "Max Health");
        en_us.put(stat_maxMana.key, "Max Mana");
        en_us.put(stat_attack.key, "Attack");
        en_us.put(stat_mAttack.key, "Magic Attack");
        en_us.put(stat_defense.key, "Defense");
        en_us.put(stat_mDefense.key, "Magic Defense");
        en_us.put(stat_luck.key, "Luck");
        en_us.put(stat_point.key, "Stat Points: %s");
        en_us.put(stat_expNeeded.key, "Exp to next level");
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

}
