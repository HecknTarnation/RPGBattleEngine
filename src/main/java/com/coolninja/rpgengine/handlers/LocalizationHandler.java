package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.Engine;
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
        en_us.put("invalidlang_err", "Invalid Language");
        //end init en_us
        langs.put("en_us", en_us);

    }

    public void changLang(String langKey) {
        if (langs.containsKey(langKey)) {
            currentLang = langs.get(langKey);
        } else {
            System.out.println(Engine.localizationHandler.getLocalizedString("invalidlang_err"));
        }
    }

    public void addLang(String key, HashMap<String, String> lang) {
        langs.put(key, lang);
    }

    public String getLocalizedString(String key) {
        return currentLang.get(key);
    }

}
