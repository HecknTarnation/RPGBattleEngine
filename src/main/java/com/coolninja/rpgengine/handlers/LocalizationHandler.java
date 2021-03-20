package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.ConsoleFunc;
import com.coolninja.rpgengine.enums.EN_USKeys;
import static com.coolninja.rpgengine.enums.EN_USKeys.*;
import java.util.HashMap;

/**
 *
 * @author Ben
 */
public class LocalizationHandler {

    public HashMap<String, HashMap<String, String>> langs = new HashMap<>();

    private HashMap<String, String> en_us = new HashMap<>();

    private HashMap<String, String> currentLang;

    /**
     * Initializes this handler, also creates the en_us language (the default
     * language)
     */
    public void init() {
        //init en_us
        en_us.put(err_invalidlang.key, "Invalid Language");
        en_us.put(err_missinglangkey.key, "Missing or Invalid Key, returning null...");
        en_us.put(battle_compidle.key, " did nothing");
        en_us.put(battle_plridle.key, "You did nothing");
        en_us.put(battle_menu.key, "Attack,Item,Idle,Run");
        en_us.put(battle_currentTurn.key, "It's %s's turn");
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

    public String getLocalizedString(EN_USKeys key) {
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
