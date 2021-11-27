package com.heckntarnation.rpgbattleengine.dev;

import com.heckntarnation.rpgbattleengine.BattleEngine;
import com.heckntarnation.rpgbattleengine.enums.LangKeys;

/**
 *
 * @author Ben
 */
public class Macros {

    //some macros for me (feel free to use them)
    public static String localize(String key) {
        return BattleEngine.localizationHandler.getLocalizedString(key);
    }

    public static String localize(LangKeys key) {
        return localize(key.key);
    }

    public static void print(LangKeys key) {
        print(localize(key));
    }

    public static void print(String str) {
        System.out.print(str);
    }

    public static void println(LangKeys key) {
        println(localize(key));
    }

    public static void println(String str) {
        System.out.println(str);
    }
}
