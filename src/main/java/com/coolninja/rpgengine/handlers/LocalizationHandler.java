package com.coolninja.rpgengine.handlers;

import com.coolninja.rpgengine.ConsoleFunc;
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
        en_us.put("missinglangkey_err", "Missing or Invalid Key, returning null...");
        //end init en_us
        langs.put("en_us", en_us);

    }

    public void changLang(String langKey) {
        if (langs.containsKey(langKey)) {
            currentLang = langs.get(langKey);
        } else {
            System.out.println(getLocalizedString("invalidlang_err"));
            ConsoleFunc.wait(1000);
        }
    }

    public void addLang(String key, HashMap<String, String> lang) {
        langs.put(key, lang);
    }

    public String getLocalizedString(String key) {
        if (!currentLang.containsKey(key)) {
            System.out.println(getLocalizedString("missinglangkey_err"));
            return null;
        }
        return currentLang.get(key);
    }

}
